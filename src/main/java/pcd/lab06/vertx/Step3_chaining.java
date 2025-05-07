package pcd.lab06.vertx;

import java.util.List;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

//esempio di esecuzione dell'event loop tramite un verticle e concatenazione delle callback
class TestChain extends AbstractVerticle {
	
	public void start() {
		FileSystem fs = vertx.fileSystem();    		

		fs.readFile("README.md").compose((Buffer buf) -> { //eseguo la prima operazione di lettura e restituisco una promise quando è completata l'operazione
			log("1 - README: \n" + buf.toString().substring(0,160));
			return fs.readFile("pom.xml"); //restituisco una promise quando è stata completata la seconda lettura
		}).compose((Buffer buf) -> { //continua
			log("2 - POM: \n" + buf.toString().substring(0,160));
			return fs.readDir("src"); //restituisco una promise quando è stata completata la terza lettura
		}).onComplete((AsyncResult<List<String>> list) -> { //termino la catena
			log("3 - DIR: \n" + list.result());
		});
		
	}

	private void log(String msg) {
		System.out.println("[ " + System.currentTimeMillis() + " ][ " + Thread.currentThread() + " ] " + msg);
	}
}

public class Step3_chaining {

	public static void main(String[] args) {
		Vertx  vertx = Vertx.vertx();
		vertx.deployVerticle(new TestChain());
	}
}

