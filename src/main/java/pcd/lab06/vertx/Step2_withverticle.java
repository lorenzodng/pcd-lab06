package pcd.lab06.vertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.*;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

/**
 * 
 * Simple Verticle - i.e. an event-loop in Vert.x
 * 
 */

//esempio di esecuzione dell'event loop tramite un "verticle"
class MyReactiveAgent extends AbstractVerticle {

	//la sua esecuzione avviene tramite il metodo start (sono coinvolti due thread: thread event loop e worker thread)
	public void start() {
		log("1 - doing the async call...");
		
		FileSystem fs = getVertx().fileSystem();  //sintassi più concisa per accedere al file system mediante un oggetto vertx
		
		Future<Buffer> f1 = fs.readFile("README.md");

		f1.onComplete((AsyncResult<Buffer> res) -> {
			log("4 - README \n" + res.result().toString().substring(0,160)); //la stampa di questa parte non è deterministica, perchè potrebbe avvenire prima o dopo alla stampa del pom.xml, a seconda del tempo impiegato per la lettura
		});
	
		log("2 - doing the second async call...");

		fs.readFile("pom.xml").onComplete((AsyncResult<Buffer> res) -> { //sintassi più concisa per eseguire l'operazione dopo la lettura del file
			log("4 - POM \n" + res.result().toString().substring(0,160)); //la stampa di questa parte non è deterministica, perchè potrebbe avvenire prima o dopo alla stampa del README.md, a seconda del tempo impiegato per la lettura
		});
		
		log("3 - done");
	}

	private void log(String msg) {
		System.out.println("[ " + System.currentTimeMillis() + " ][ " + Thread.currentThread() + " ] " + msg);
	}
}

public class Step2_withverticle {

	//il thread main esegue solo questa parte
	public static void main(String[] args) {
		Vertx  vertx = Vertx.vertx(); //creo un oggetto vertx
		vertx.deployVerticle(new MyReactiveAgent()); //mando in esecuzione il verticle
	}
}

