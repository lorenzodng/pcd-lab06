package pcd.lab06.rx;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

//creazione di un flusso "hot" e utilizzo tramite un thread separato creato in modo esplicito
public class Test02d_creation_hot {

	public static void main(String[] args) throws Exception {

		System.out.println("\n=== TEST Hot streams ===\n");
		
		Observable<Integer> source = Observable.create(emitter -> {
			new Thread(() -> {
				for (int i = 0; i < 200; i++) {
					log("source: " + i);
					emitter.onNext(i);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
				emitter.onComplete();
			}).start();
		});

		//rendo il flusso "hot", ovvero lo creo prima ancora che possa essere eseguito subscribe su di esso
		ConnectableObservable<Integer> hotObservable = source.publish();
		hotObservable.connect();
	
		Thread.sleep(500);
		
		log("Subscribing A.");
		
		hotObservable.subscribe((s) -> { //osservo gli elementi del flusso già in esecuzione
			log("subscriber A: "+s); //in particolare, sono stampati solo gli elementi generati a partire dal momento in cui viene eseguito subscribe
		});
		
		Thread.sleep(500);
		
		log("Subscribing B.");
		
		hotObservable.subscribe((s) -> {
			log("subscriber B: "+s); 
		});	
		
		log("Done.");
		
		Thread.sleep(10_000);

	}
	
	static private void log(String msg) {
		System.out.println("[ " + Thread.currentThread().getName() + "  ] " + msg);
	}
	

}
