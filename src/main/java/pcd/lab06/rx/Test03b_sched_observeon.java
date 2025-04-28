package pcd.lab06.rx;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

//creazione di un flusso cold tramite un thread e utilizzo tramite un pool di thread
public class Test03b_sched_observeon {

	public static void main(String[] args) throws Exception {
		
		System.out.println("\n=== TEST observeOn ===\n");

		Observable.just(100)
			.map(v -> { //(eseguito dal thread main)
				log("map 1 " + v);
				return v * v;
			})
			.observeOn(Schedulers.computation()) //il pool di thread esegue l'operazione map sottostante
			.map(v -> {
				log("map 2 " + v);
				return v + 1;
			})
			.subscribe(v -> { //il thread main crea il flusso
				log("sub " + v);
			});

		Thread.sleep(100);

		System.out.println("\n=== TEST observeOn with blockingSubscribe ===\n");

		Observable.just(100)	
			.map(v -> { log("map 1 " + v); return v * v; })
			.observeOn(Schedulers.computation())
			.map(v -> { log("map 2 " + v); return v + 1; })
			.blockingSubscribe(v -> {
				log("sub " + v);
			});

		
		System.out.println("\n=== TEST observeOn with multiple subs ===\n");

		Observable<Integer> src2 = Observable.just(100)	
				.map(v -> { log("map 1 " + v); return v * v; })		// by the current thread (main thread)
				.observeOn(Schedulers.computation()) 				// => use RX comp thread(s) downstream
				.map(v -> { log("map 2 " + v); return v + 1; });		// by the RX comp thread;
		
		src2.subscribe(v -> {						// by the RX comp thread
			log("sub 1 " + v);
		});

		src2.subscribe(v -> {						// by the RX comp thread
			log("sub 2 " + v);
		});

		Thread.sleep(100);

		
	}
		
	static private void log(String msg) {
		System.out.println("[" + Thread.currentThread().getName() + "] " + msg);
	}
	
}
