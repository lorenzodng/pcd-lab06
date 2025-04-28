package pcd.lab06.vertx;

import io.vertx.core.*;


/**
 * 
 * Exercise
 * 
 * - implement an async protected method getDelayedRandom(int delay)
 *   that returns a random value between 0 and 1 after the specified
 *   amount of time, using promises 
 *   
 *     -- refer to Promise, Future and setTimer API in Vert.x
 *    
 * - in the "start" method, test the behaviour of the method by 
 *   doing a request with 1 sec delay and printing the random result.
 * 
 */

//esercizio di esecuzione dell'event loop tramite un verticle e promise esplicita
class VerticleWithPromise extends AbstractVerticle {
	
	public void start() {
		log("started.");
		Future<Double> fut= getDelayedRandom(1000); //metodo che genera un evento
		fut.onComplete((res) -> { //callback/handler
			log("Result: " + res.result()); //prelevo il valore della promise
		});
	}

	protected Future<Double> getDelayedRandom(int delay){
		Promise<Double> promise= Promise.promise(); //definisco una promise "esplicita", utile per ritornare il risultato all'event loop
		getVertx().setTimer(delay, (res)->{ //setTimer esegue, dopo un determinato delay, l'operazione che genera l'evento
			double num= Math.random();
			promise.complete(num); //la promise viene completata assegnandole il valore desiderato
		});
        return promise.future(); //restituisco il valore della promise
    }
	
	private void log(String msg) {
		System.out.println("[ " + System.currentTimeMillis() + " ][ " + Thread.currentThread() + " ] " + msg);
	}
}

public class Step4_promise_exercise {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new VerticleWithPromise());
	}
}

