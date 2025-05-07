package pcd.lab06.vertx;

import io.vertx.core.*;
import io.vertx.core.eventbus.EventBus;

class MyAgent1 extends AbstractVerticle {
	
	 public void start(Promise<Void> startPromise) {
		log("started.");
		EventBus eb = this.getVertx().eventBus();
		eb.consumer(Step7_EventBus.TOPIC_NAME, message -> {
			log("received new message: " + message.body());
		});		
		log("Ready.");
		startPromise.complete();
	}

	private void log(String msg) {
		System.out.println("[ " + System.currentTimeMillis() + " ][ Agent1 ] " + msg);
	}
}

class MyAgent2 extends AbstractVerticle {
	
	public void start() {
		log("started.");
		EventBus eb = this.getVertx().eventBus();
		var msg = "test";
		log("sending the message: " + msg + " on the event bus (topic: " + Step7_EventBus.TOPIC_NAME + ")...");
		eb.publish(Step7_EventBus.TOPIC_NAME, msg);
	}

	private void log(String msg) {
		System.out.println("[ " + System.currentTimeMillis() + " ][ Agent2 ] " + msg);
	}
}

public class Step7_EventBus {

	static final String TOPIC_NAME = "my-topic";

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new MyAgent1(), res -> {
			/* deploy the second verticle only when the first has completed */
			vertx.deployVerticle(new MyAgent2());
		});
	}
}

