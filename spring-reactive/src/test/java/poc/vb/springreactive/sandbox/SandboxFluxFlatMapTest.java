package poc.vb.springreactive.sandbox;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import poc.vb.springreactive.model.CustomException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static reactor.core.scheduler.Schedulers.parallel;

public class SandboxFluxFlatMapTest {
	

	List<String> elements = Arrays.asList("a","b","c");
	



	@Test
	public void fluxErrorHanling() {
		Flux<String> elementFlux = Flux.fromIterable(elements)
				.concatWith(Flux.error(new RuntimeException("Exception in flux")))
				.concatWith(Flux.just("D"))
				.onErrorResume((e) -> {
					System.err.println("E is "+ e);
					return Flux.just("default");
				}); 
		
		StepVerifier.create(elementFlux.log())
		.expectNext("a","b","c")
//		.expectError(RuntimeException.class)
		.expectNext("default")
		.verifyComplete();
		
	}
	
	@Test
	public void fluxErrorHanling_return() {
		Flux<String> elementFlux = Flux.fromIterable(elements)
				.concatWith(Flux.error(new RuntimeException("Exception in flux")))
				.concatWith(Flux.just("D"))
				.onErrorReturn("default"); 
		
		StepVerifier.create(elementFlux.log())
		.expectNext("a","b","c")
		.expectNext("default")
		.verifyComplete();
		
	}
	
	@Test
	public void fluxErrorHanling_map() {
		Flux<String> elementFlux = Flux.fromIterable(elements)
				.concatWith(Flux.error(new RuntimeException("Exception in flux")))
				.concatWith(Flux.just("D"))
				.onErrorMap((e) -> new CustomException(e)); 
		
		StepVerifier.create(elementFlux.log())
		.expectNext("a","b","c")
		.expectError(CustomException.class)
		.verify();
		
	}
	
	@Test
	public void fluxErrorHanling_map_retry() {
		Flux<String> elementFlux = Flux.fromIterable(elements)
				.concatWith(Flux.error(new RuntimeException("Exception in flux")))
				.concatWith(Flux.just("D"))
				.onErrorMap((e) -> new CustomException(e))
				.retry(2); 
		
		StepVerifier.create(elementFlux.log())
		.expectNext("a","b","c")
		.expectNext("a","b","c")
		.expectNext("a","b","c")
		.expectError(CustomException.class)
		.verify();
		
	}
	
	
}
