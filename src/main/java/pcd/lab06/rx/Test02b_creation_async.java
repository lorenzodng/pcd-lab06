package pcd.lab06.rx;

import io.reactivex.rxjava3.core.*;

//utilizzo di un flusso cold tramite un thread separato creato in modo esplicito
public class Test02b_creation_async {

	public static void main(String[] args) throws Exception {

		log("Creating an observable (cold) using its own thread."); //(eseguito dal thread main)

		Observable<Integer> source = Observable.create(emitter -> { //(eseguito dal thread main)
			new Thread(() -> { //(eseguito dal thread main)
				for (int i = 0; i < 20; i++) { //(da qui in poi eseguito dal nuovo thread)
					log("source: " + i);
					emitter.onNext(i);
					try {
						Thread.sleep(200);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
				emitter.onComplete();
			}).start();
		});

		Thread.sleep(1000); //(eseguito dal thread main)
		
		log("Subscribing A."); //(eseguito dal thread main)

		source.subscribe((s) -> { //(eseguito dal thread main)
			log("Subscriber A: " + s);
		});

		Thread.sleep(1000); //(eseguito dal thread main)

		log("Subscribing B."); //(eseguito dal thread main)

		source.subscribe((s) -> { //(eseguito dal thread main)
			log("Subscriber B: " + s);
		});

		log("Done."); //(eseguito dal thread main)
	}
	
	static private void log(String msg) {
		System.out.println("[ " + Thread.currentThread().getName() + " ] " + msg);
	}

}
