package pcd.lab06.rx;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class Test02e_creation_hot_pubsub {

	public static void main(String[] args) throws Exception {

		System.out.println("\n=== TEST Hot streams with pubsub ===\n");

		PublishSubject<Integer> source = PublishSubject.<Integer>create();
		 
		log("subscribing.");

		source.subscribe((s) -> {
			log("subscriber A: "+s); 
		}, Throwable::printStackTrace);
		 
		log("generating.");

		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				log("source: " + i);
				source.onNext(i);
				try {
					Thread.sleep(10);
				} catch (Exception ex) {
				}
			}
		}).start();

		log("waiting.");

		Thread.sleep(100);
		
		source.subscribe((s) -> {
			log("subscriber B: "+s); 
		}, Throwable::printStackTrace);

	}
	
	static private void log(String msg) {
		System.out.println("[ " + Thread.currentThread().getName() + "  ] " + msg);
	}
	

}
