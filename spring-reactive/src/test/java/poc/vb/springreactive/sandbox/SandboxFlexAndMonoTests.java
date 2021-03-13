package poc.vb.springreactive.sandbox;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class SandboxFlexAndMonoTests {

	
	@Test
	public void fluxTest() {
		
		Flux<String> stringFLux= Flux.just("a","b","c");
		stringFLux.subscribe(System.out::println);
		
	}
	
	@Test
	public void fluxTestError() {
		
		Flux<String> stringFLux= 
				Flux.just("a","b","c")
				.concatWith(Flux.error(new RuntimeException("exception on flux")))
				.concatWith(Flux.just("d"))
				.log();
		
		stringFLux.subscribe(System.out::println,
				(e) -> System.err.println("subscribe capture " +e));
		
	}
	
	@Test
	public void fluxTestErrorAfterError() {
		
		Flux<String> stringFLux= 
				Flux.just("a","b","c")
				.concatWith(Flux.error(new RuntimeException("exception on flux")))
				.concatWith(Flux.just("d")) // D is not present stream is blocked
				.log();
		
		stringFLux.subscribe(System.out::println,
				(e) -> System.err.println("subscribe capture " +e),
				() -> System.err.println("Event is completed "));
		
	}
	
	@Test
	public void fluxTestElement_WithoutErrors() {
		
		Flux<String> stringFLux= 
				Flux.just("a","b","c")
				.log();
		
		StepVerifier.create(stringFLux)
		.expectNext("a")
		.expectNext("b")
		.expectNext("c")
		.verifyComplete();
		
		// if a change the order test fail
		// verifyComplete need to be present, otherwise Flux is not subscibed
		
	}
	
	@Test
	public void fluxTestElement_WithErrors() {
		
		Flux<String> stringFLux= 
				Flux.just("a","b","c")
				.concatWith(Flux.error(new RuntimeException("exception on flux")))
				.log();
		
		StepVerifier.create(stringFLux)
		.expectNext("a")
		.expectNext("b")
		.expectNext("c")
		.expectError(RuntimeException.class)
		.verify();
		
		// verify need to be present, otherwise Flux is not subscibed
		
				
	}
	
	@Test
	public void fluxTestElement_WithErrorsMessage() {
		
		Flux<String> stringFLux= 
				Flux.just("a","b","c")
				.concatWith(Flux.error(new RuntimeException("exception on flux")))
				.log();
		
		StepVerifier.create(stringFLux)
		.expectNext("a")
		.expectNext("b")
		.expectNext("c")
		.expectErrorMessage("exception on flux")
		.verify();
		
		// verify need to be present, otherwise Flux is not subscibed
				
	}
	
	public void fluxTestElementCount_WithErrors() {
		
		Flux<String> stringFLux= 
				Flux.just("a","b","c")
				.concatWith(Flux.error(new RuntimeException("exception on flux")))
				.log();
		
		StepVerifier.create(stringFLux)
		.expectNextCount(3)
		.expectErrorMessage("exception on flux")
		.verify();
		
		// verify need to be present, otherwise Flux is not subscibed
				
	}
	
	@Test
	public void monoTest() {
		
		Mono<String> stringMono= Mono.just("a")
				.log();
		
		StepVerifier.create(stringMono).expectNext("a").verifyComplete();
		
	}
	
	@Test
	public void monoTest_onError() {
		
		StepVerifier.create(Mono.error(new RuntimeException("Mono error")))
		.expectError(RuntimeException.class)
		.verify();
		
	}
	
	
	
	

	
	
	
}
