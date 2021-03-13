package poc.vb.springreactive.service;

import org.springframework.stereotype.Service;

import poc.vb.springreactive.model.internal.PaymentRequest;
import reactor.core.publisher.Mono;

@Service
public class DBLoggingService {
	
	public Mono<PaymentRequest> updateStatus(PaymentRequest paymentRequest, String status) {
		
		System.out.println("Update status and save to Database " + status);	
		return Mono.just(paymentRequest.updateStatus(status));
	}

	
}
