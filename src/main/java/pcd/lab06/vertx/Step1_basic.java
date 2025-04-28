	package pcd.lab06.vertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import java.io.*;

public class Step1_basic {

	//esempio di esecuzione dell'event loop (sono coinvolti tre thread: thread main (il codice è eseguito nel main), thread event loop, worker thread)
	public static void main(String[] args) {

		System.out.println(new File(".").getAbsoluteFile()); //(eseguito dal thread main)
		
		Vertx vertx = Vertx.vertx(); //creo un oggetto vertx che rappresenta il thread dell'event loop (eseguito dal thread main)

		FileSystem fs = vertx.fileSystem(); //accedo al file system (eseguito dal thread main)

		log("doing the async call... "); //(eseguito dal thread main)
		
		Future<Buffer> fut = fs.readFile("README.md"); //definisco una promise, restituita una volta che il file è stato letto, ed eseguo l'operazione di lettura. In particolare, trattandosi di un'operazione bloccante, l'operazione viene automaticamente delegata a un worker thread (readFile: invocata dal thread main -> delegata dal thread event loop -> eseguita dal worker thread).

		fut.onComplete((AsyncResult<Buffer> res) -> { //configuro la promise in modo che, quando è stata completata l'operazione di lettura, eseguo la stampa dei risultati (onComplete corrisponde a then di JavaScript, e viene eseguito sia in caso di successo che di errore delle operazioni per cui le promises sono restituite) (eseguito dal thread event loop)
			log("README \n" + res.result().toString().substring(0,160)); //stampo i primi 160 caratteri del risultato (res "capisce" di dover restituire i risultati del file perchè è un parametro della promise ottenuta come operazione di lettura su quel file) (eseguito dal thread event loop)
		});

		log("async call done. Waiting some time... "); //(eseguito dal thread main)

		try {
			Thread.sleep(1000); //(eseguito dal thread main)
		} catch (Exception ex) {}
		
		log("done"); //(eseguito dal thread main)
	}
	
	private static void log(String msg) {
		System.out.println("[ " + System.currentTimeMillis() + " ][ " + Thread.currentThread() + " ] " + msg);
	}
}

