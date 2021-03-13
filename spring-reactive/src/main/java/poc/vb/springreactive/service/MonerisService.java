package poc.vb.springreactive.service;

import org.springframework.stereotype.Service;

import poc.vb.springreactive.model.CustomException;
import poc.vb.springreactive.model.internal.PaymentRequest;
import reactor.core.publisher.Mono;

@Service
public class MonerisService {
	
	public Mono<PaymentRequest> purchase(PaymentRequest pr) {

		
		return pr.getId().contains("billing-error") ? Mono.just(pr.updateStatus("NOT_PURCHASED")) : Mono.just(pr.updateStatus("PURCHASED"));
		
	}
	

}
