package poc.vb.springreactive.sandbox;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class SandboxFlexFilterTests {

	
	@Test
	public void fluxTestFilter() {
		
		Flux<String> stringFLux= 
				Flux.just("a","aa","c")
				.filter(e -> e.contains("a")) 
				.log();
		
		StepVerifier.create(stringFLux)
		.expectNext("a", "aa")
		.verifyComplete();
		
	}
	
	
	
}
