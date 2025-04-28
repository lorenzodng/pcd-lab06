package pcd.lab06.rx;

import io.reactivex.rxjava3.core.*;

//creazione di un flusso cold con emitter
public class Test02a_creation_synch {

	public static void main(String[] args){
		
	    log("Creating a observable (cold).");

	    Observable<Integer> source = Observable.create(emitter -> { //definisco un flusso secondo uno specifico criterio (emitter è l'oggetto che permette generare gli elementi)
	        for (int i = 0; i <= 2; i++) {
	            log("source: " + i);
	            emitter.onNext(i); //genero 3 elementi nel flusso (0, 1, 2)
	        }
	        emitter.onComplete(); //segnalo il completamento dell'operazione
	    });

	    log("Subscribing A");
	    
	    source.subscribe(v -> log("A: "+v)); //creo il flusso e stampo gli elementi (modo alternativo per stampare gli elementi)
	}
	
	static private void log(String msg) {
		System.out.println("[ " + Thread.currentThread().getName() + " ] " + msg);
	}
}
