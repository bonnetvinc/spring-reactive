package poc.vb.springreactive.sandbox;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static reactor.core.scheduler.Schedulers.parallel;

public class SandboxCombineFluxTest {
	
	List<String> elements1 = Arrays.asList("a","b","c");
	List<String> elements2 = Arrays.asList("d","e","f");
	


	@Test
	public void combineUsingMerge() {
		Flux<String> element1Flux = Flux.fromIterable(elements1);
		Flux<String> element2Flux = Flux.fromIterable(elements2);
				
		Flux<String> merged = Flux.merge(element1Flux,element2Flux);
		
		StepVerifier.create(merged)
		.expectSubscription()
		.expectNextCount(6)
		.verifyComplete();
		
	}
	
	@Test
	public void combineUsingMerge_delai() {
		Flux<String> element1Flux = Flux.fromIterable(elements1).delayElements(Duration.ofSeconds(2));
		Flux<String> element2Flux = Flux.fromIterable(elements2).delayElements(Duration.ofSeconds(2));
				
		Flux<String> merged = Flux.merge(element1Flux,element2Flux).log();
		
		StepVerifier.create(merged)
		.expectSubscription()
//		.expectNext("a","b","c","d", "e","f") order change between elements
		.verifyComplete();
		
	}
	
	@Test
	public void combineUsingConcat() {
		Flux<String> element1Flux = Flux.fromIterable(elements1);
		Flux<String> element2Flux = Flux.fromIterable(elements2);
				
		Flux<String> merged = Flux.concat(element1Flux,element2Flux).log();
		
		StepVerifier.create(merged)
		.expectSubscription()
		.expectNextCount(6)
		.verifyComplete();
		
	}
	
	@Test
	public void combineUsingConcat_delay() {
		Flux<String> element1Flux = Flux.fromIterable(elements1).delayElements(Duration.ofSeconds(1));
		Flux<String> element2Flux = Flux.fromIterable(elements2);
		
		// with concat the order is maintained
		Flux<String> merged = Flux.concat(element1Flux,element2Flux).log();
		
		
		StepVerifier.create(merged)
		.expectSubscription()
		.expectNextCount(6)
		.verifyComplete();
		
	}
	
	@Test
	public void combineUsingConcat_zip() {
		Flux<String> element1Flux = Flux.fromIterable(elements1).delayElements(Duration.ofSeconds(1));
		Flux<String> element2Flux = Flux.fromIterable(elements2);
		
		// with concat the order is maintained
		Flux<String> merged = Flux.zip(element1Flux,element2Flux, (t1, t2) -> {
			return t1.concat(t2);
		}).log();
		
		
		StepVerifier.create(merged)
		.expectSubscription()
		.expectNext("ad","be", "cf")
		.verifyComplete();
		
	}
	
}
