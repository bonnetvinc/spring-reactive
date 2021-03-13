package poc.vb.springreactive;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import poc.vb.springreactive.core.Orchestrator;
import poc.vb.springreactive.model.internal.PaymentRequest;
import poc.vb.springreactive.service.BillingService;
import reactor.core.publisher.Mono;

@SpringBootTest
public class Intergration {
	
	@Autowired
	Orchestrator orchestrator;
	
	@Autowired
	BillingService billingService;
	
	
	@Test
	public void pay() {
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setAmount("10000");
		paymentRequest.setId("ok");
		
		Mono<String> response = orchestrator.paySingle(paymentRequest);
		response.subscribe(System.out::println);
	}

	@Test
	public void pay_billing_error() {
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setAmount("10000");
		paymentRequest.setId("billing-error");
		
		Mono<String> response = orchestrator.paySingle(paymentRequest);
		response.subscribe(System.out::println);
	}
	
	@Test
	public void pay_preauth_error() {
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setAmount("10000");
		paymentRequest.setId("preauth-error");
		
		Mono<String> response = orchestrator.paySingle(paymentRequest);
		response.subscribe(System.out::println);
	}
	
	@Test
	public void pay_billing_exception() {
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setAmount("10000");
		paymentRequest.setId("billing-exc");
		
		Mono<String> response = orchestrator.paySingle(paymentRequest);
		response.subscribe(System.out::println);
	}
	
	@Test
	public void pay_preauth_exception() {
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setAmount("10000");
		paymentRequest.setId("preauth-exc");
		
		Mono<String> response = orchestrator.paySingle(paymentRequest);
		response.subscribe(System.out::println);
	}

}
