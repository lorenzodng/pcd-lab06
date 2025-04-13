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
class VerticleWithPromise extends AbstractVerticle {
	
	public void start() {
		log("started.");	
	}

	protected Future<Double> getDelayedRandom(int delay){
		return null;
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

