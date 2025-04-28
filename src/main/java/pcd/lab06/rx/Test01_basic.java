package pcd.lab06.rx;

import java.util.Arrays;

import io.reactivex.rxjava3.core.*;

//creazione e utilizzo di un flusso "cold"
public class Test01_basic {

	public static void main(String[] args){
				
		log("creating with just.");

	    Observable.just("Hello world").subscribe(s -> {	//definisco un flusso costituito da un solo elemento (stringa "Hello world"), e mi sottoscrivo per crearlo e per stampare l'elemento (Observable è una classe per rappresentare flussi dedicati alla gestione di pochi dati)
			log(s);
	    });

	    Flowable.just("Hello world").subscribe(System.out::println); //modo alternativo (Flowable è una classe per rappresentare flussi dedicati alla gestione di molti dati)

		String[] words = { "Hello", " ", "World", "!" };
		
		Flowable.fromArray(words).subscribe((String s) -> { //definisco un flusso di elementi presenti nell'array e mi sottoscrivo crearlo e per stampare tutti gli elementi
			log(s);
		});

		log("Full subscription...");
		
		Observable.fromArray(words).subscribe((String s) -> { //definisco un flusso di elementi presenti nell'array e mi sottoscrivo per crearlo e per:
			log("> " + s); //stampare tutti gli elementi
		},(Throwable t) -> {
			log("error  " + t); //mostrare un errore quando si verifica un problema
		},() -> {
			log("completed"); //mostrare una stringa quando tutti gli elementi sono stati stampati
		});

		log("simple application of operators");
		
		Flowable<Integer> flow = Flowable.range(1, 20).map(v -> v * v).filter(v -> v % 3 == 0); //definisco un flusso di 20 interi (da 1 a 20), da cui creo un nuovo flusso in cui ogni intero è moltiplicato per sè stesso, da cui creo un nuovo flusso contenente solo gli interi divisibili per 3
		
		log("first subscription #1");
		flow.subscribe(System.out::println); //stampo il flusso ottenuto in precedenza

		log("first subscription #2");
		flow.subscribe((v) -> {
			log("" + v);
		});

		log("showing the flow...");
		
		Flowable.range(1, 20)
			.doOnNext(v -> log("1> " + v)).map(v -> v * v)
			.doOnNext(v -> log("2> " + v)).filter(v -> v % 3 == 0)
			.doOnNext(v -> log("3> " + v)).subscribe(System.out::println);
						

		log("simple composition");
		
		Observable<String> src1 = Observable.fromIterable(Arrays.asList(
				 "the",
				 "quick",
				 "brown",
				 "fox",
				 "jumped",
				 "over",
				 "the",
				 "lazy",
				 "dog"
		));

		Observable<Integer> src2 = Observable.range(1, 5);
		
		src1.zipWith(src2, (string, count) -> String.format("%2d. %s", count, string)).subscribe(System.out::println);
		
	}
	
	private static void log(String msg) {
		System.out.println("[" + Thread.currentThread().getName() + "] " + msg);
	}
}
