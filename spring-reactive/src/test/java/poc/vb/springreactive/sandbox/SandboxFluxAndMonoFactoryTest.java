package poc.vb.springreactive.sandbox;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class SandboxFluxAndMonoFactoryTest {
	
	List<String> elements = Arrays.asList("a","b","c");
	
	@Test
	public void fluxIterable() {
		Flux<String> elementFlux = Flux.fromIterable(elements).log();
		
		StepVerifier.create(elementFlux)
		.expectNext("a","b","c")
		.verifyComplete();
	}
	
	@Test
	public void fluxUsingArray() {
		
		String[] elementsArray = new String[] {"a","b","c"};
		Flux<String> elementFlux = Flux.fromArray(elementsArray).log();
		
		StepVerifier.create(elementFlux)
		.expectNext("a","b","c")
		.verifyComplete();
	}
	
	@Test
	public void fluxUsingStream() {
		
		Flux<String> elementFlux = Flux.fromStream(elements.stream()).log();
		
		StepVerifier.create(elementFlux)
		.expectNext("a","b","c")
		.verifyComplete();
	}
	
	@Test
	public void monoEmptyUsingJustOrEmpty() {
		
		Mono<String> mono = Mono.justOrEmpty(null);
		
		StepVerifier.create(mono.log())
		.verifyComplete();
	}
	
	@Test
	public void monoUsingSupplier() {
		
		Supplier<String> sup = () -> "a";
		
		Mono<String> mono = Mono.fromSupplier(sup);
		
		StepVerifier.create(mono.log())
		.expectNext("a")
		.verifyComplete();
	}

}
