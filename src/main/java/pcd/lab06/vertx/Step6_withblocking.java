package pcd.lab06.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

//esempio di esecuzione dell'event loop e di un worker thread esplicito
class TestExecBlocking extends AbstractVerticle {

	public void start() {
		log("before");

		Future<Integer> res = getVertx().executeBlocking(() -> { //definisco il blocco di codice che verrà eseguito da un worker thread (dato che il worker thread viene automaticamente richiamato da vertx per operazioni che interpretate come bloccanti, è utile definire un blocco di codice per un worker thread solo quando devono essere delegate anche operazioni non bloccanti)
			log("blocking computation started");
			try {
				Thread.sleep(5000); //fermo il worker thread per 5 secondi
				return 100;
			} catch (Exception ex) {
				throw new Exception("exception");
			}
		});

		log("after triggering a blocking computation...");

		res.onComplete((AsyncResult<Integer> r) -> {
			log("result: " + r.result());
		});
		
		res.onSuccess((flatResult) -> {
			log("result: " + flatResult);

		});
	}

	private void log(String msg) {
		System.out.println("[ " + System.currentTimeMillis() + " ][ " + Thread.currentThread() + " ] " + msg);
	}
}

public class Step6_withblocking {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new TestExecBlocking());
	}
}
