PCD a.y. 2024-2025 - ISI LM UNIBO - Cesena Campus

# Lab Activity #06 - 20250413

v.1.0.0-20250322

### Assignment #02 presentation
- [Assignment 02 description](https://github.com/pcd-2024-2025/assignment-02)

### Asynchronous Programming in Java using Vert.x

- Docs about [Vert.x Framework](http://vertx.io/). Key points:
    - Reactor and Multi-Reactor, The Golden Rule - Don’t Block the Event Loop
    - "Verticle" abstraction => event loop
        - multiple verticles => multiple independent event loops 
    - Async programming style
        - based on (async) "future" + promise
        - future composition and coordination
    - How to run blocking code or long-term computation (`executeBlocking` mechanism)
    - Verticles asynchronous communication using the Event Bus 
    - Libraries. Among the others:
        - file systems, network (e.g. http) 
- Basic examples in `pcd.lab06.vertx`
    - Simple examples (through steps)  showing some core API at work  
    - Exercise - Step 4 (about promises)
        - Complete the VerticleWithPromise verticle by: Implementing an async protected method getDelayedRandom(int delay) that returns a random value between 0 and 1 after the specified amount of time, using promises. In the "start" method of the verticle, test the behaviour of the method by using it API to be used: Promise, Future, setTimer (see Vert.x core doc and Javadoc)

### Reactive Programming in Java using RxJava

- Docs about [ReactiveX]( http://reactivex.io/) and [RxJava](https://github.com/ReactiveX/RxJava). Key points: see `module-2.2 - Reactive Programming`
- Basic examples in `pcd.lab06.rx`
   - Simpl examples about observables & flowables, operators, subscribers
   - Documentation about [Operators](http://reactivex.io/documentation/operators.html)
   - Concurrency in observers computation: schedulers
   - Creation of observables - sync, async, cold and hot
   - Hot observables (flowables) & backpressure operators
   - Reactive Programming for GUI programming - Swing example

