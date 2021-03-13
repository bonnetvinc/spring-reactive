package poc.vb.springreactive.sandbox;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static reactor.core.scheduler.Schedulers.parallel;

public class SandboxErrorManagementTest {
	
	List<String> elements = Arrays.asList("a","b","c","d", "e","f");
	
	List<String> serviceCall(String s) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Arrays.asList(s, "newResponse");
	}
	

	@Test
	public void fluxTransform_flatmap() {
		Flux<String> elementFlux = Flux.fromIterable(elements)
				.flatMap(s -> {
					return Flux.fromIterable(serviceCall(s));
				}).log(); // external service thats returns a flux
		
		StepVerifier.create(elementFlux)
		.expectNextCount(12)
		.verifyComplete();
		
	}
	
	@Test
	public void fluxTransform_flatmap_parallel() {
		
		List<String> elements7 = Arrays.asList("a","b","c","d", "e","f","g");
		
		 Flux<String> stringFlux = Flux.fromIterable(elements7)
				 .window(2) 
	             .flatMap((s) ->
	                    s.map(this::serviceCall).subscribeOn(parallel())) 
	                    .flatMap(s -> Flux.fromIterable(s)) 
	                .log();

	        StepVerifier.create(stringFlux)
	                .expectNextCount(14)
	                .verifyComplete();	
	}
	
	@Test
	public void fluxTransform_flatMapSequential_parallel_ordered() {
		
		List<String> elements7 = Arrays.asList("a","b","c","d", "e","f","g");
		
		 Flux<String> stringFlux = Flux.fromIterable(elements7)
				 .window(2) 
	             .flatMapSequential((s) ->
	                    s.map(this::serviceCall).subscribeOn(parallel())) 
	                    .flatMap(s -> Flux.fromIterable(s)) 
	                .log();

	        StepVerifier.create(stringFlux)
	                .expectNextCount(14)
	                .verifyComplete();
		
	}
	
	

}
