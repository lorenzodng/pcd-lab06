package pcd.lab06.rx;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

//utilizzo di un flusso cold tramite un pool di thread seperati
public class Test03a_sched_subscribeon {

	public static void main(String[] args) throws Exception {

		System.out.println("\n=== TEST subscribeOn ===\n");

		Observable<Integer> src = Observable.just(100)
			.map(v -> {
				log("map 1 " + v);
				return v * v;
			})
			.map(v -> {
				log("map 2 " + v);
				return v + 1;
			});

		src.subscribeOn(Schedulers.computation()).subscribe(v -> { //definisco un pool di thread (di dimensione: numero core + 1) che utilizzano il flusso
			log("sub 1 " + v);
		});

		src.subscribeOn(Schedulers.computation()).subscribe(v -> {
			log("sub 2 " + v);
		});

		Thread.sleep(100);
		
		System.out.println("\n=== TEST parallelism ===\n");

		/*
		 * Running independent flows on a different scheduler
		 * and merging their results back into a single flow
		 * warning: flatMap => no order in merging
		 */

		Flowable.range(1, 10)
		  .flatMap(v ->
		      Flowable.just(v)
		      	.subscribeOn(Schedulers.computation())
				.map(w -> { 
					log("map " + w); 
					return w * w; 
				})		
		  )
		  .blockingSubscribe(v -> {
			 log("sub > " + v); 
		  });
		
	}
		
	static private void log(String msg) {
		System.out.println("[" + Thread.currentThread().getName() + "] " + msg);
	}
	
}
