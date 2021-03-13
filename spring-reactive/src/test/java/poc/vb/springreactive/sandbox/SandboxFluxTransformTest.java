package poc.vb.springreactive.sandbox;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class SandboxFluxTransformTest {
	
	List<String> elements = Arrays.asList("a","b","c");
	
	@Test
	public void fluxTransform() {
		Flux<String> elementFlux = Flux.fromIterable(elements)
				.map(s -> s.toUpperCase()).log();
		
		StepVerifier.create(elementFlux)
		.expectNext("A","B","C")
		.verifyComplete();
	}
	
	@Test
	public void fluxTransform_length() {
		Flux<Integer> elementFlux = Flux.fromIterable(elements)
				.map(s -> s.length()).log();
		
		StepVerifier.create(elementFlux)
		.expectNext(1,1,1)
		.verifyComplete();
	}
	
	@Test
	public void fluxTransform_length_repeat() {
		Flux<Integer> elementFlux = Flux.fromIterable(elements)
				.map(s -> s.length())
				.repeat(2)
				.log();
		
		StepVerifier.create(elementFlux)
		.expectNext(1,1,1,1,1,1,1,1,1)
		.verifyComplete();
		
	}
	
	@Test
	public void fluxTransform_filter_length_repeat() {
		Flux<String> elementFlux = Flux.fromIterable(elements)
				.filter(s-> s.contains("a"))
				.map(s -> s.toUpperCase())
				.log();
		
		StepVerifier.create(elementFlux)
		.expectNext("A")
		.verifyComplete();
		
	}
	
	

}
