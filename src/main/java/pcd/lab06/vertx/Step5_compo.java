package pcd.lab06.vertx;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

//esempio di esecuzione dell'event loop tramite un verticle e promise all
class TestCompo extends AbstractVerticle {
	
	public void start() {
		FileSystem fs = vertx.fileSystem();    		

		Future<Buffer> f1 = fs.readFile("README.md"); //(eseguito dal primo worker thread)
		Future<Buffer> f2 = fs.readFile("POM.xml"); //(eseguito dal secondo worker thread)
				
		Future.all(f1,f2).onSuccess((CompositeFuture res) -> { //quando tutte le promise sono state restituite, eseguo l'handler (onSuccess è come onComplete, ma è richiamato solo in caso di successo delle operazioni per cui le promises sono restituite)
			log("COMPOSITE => \n"+res.result().list());			
		}); 
	}

	private void log(String msg) {
		System.out.println("[ " + System.currentTimeMillis() + " ][ " + Thread.currentThread() + " ] " + msg);
	}
}

public class Step5_compo {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new TestCompo());
	}
}

