package pcd.lab06.rx;

import io.reactivex.rxjava3.core.*;

//utilizzo di un flusso cold tramite un thread separato creato con fromCallable
public class Test02c_creation_fromCallable {

	public static void main(String[] args) throws Exception {

		System.out.println("\n=== TEST fromCallable | main thread ===\n"); //(eseguito dal thread main)

		Flowable.fromCallable(() -> { //genero automaticamente un thread separato che esegue il codice sottostante
		    log("started.");
		    Thread.sleep(1000); 
		    log("completed.");
		    return "Done";
		}).subscribe(s -> {
			log("result: " + s);
		});
		
		Thread.sleep(2000); //(eseguito dal thread main)
	}
	
	static private void log(String msg) {
		System.out.println("[ " + Thread.currentThread().getName() + " ] " + msg);
	}

}
