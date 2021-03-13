package poc.vb.springreactive.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class BillingService {
	
	public Mono<Boolean> updateInformationsMono(String id) {
		
		System.out.println("updateInformationsMono " + id);	
		return Mono.just(updateInformations(id));
	}
	
	public boolean updateInformations(String id) {
		return id.contains("billing-error") ? false : true;
	}

}
