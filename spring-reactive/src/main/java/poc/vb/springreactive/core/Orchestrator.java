package poc.vb.springreactive.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.vb.springreactive.model.internal.PaymentRequest;
import poc.vb.springreactive.service.BillingService;
import poc.vb.springreactive.service.CommunicationService;
import poc.vb.springreactive.service.DBLoggingService;
import poc.vb.springreactive.service.MonerisService;
import reactor.core.publisher.Mono;

@Service
public class Orchestrator {
	
	@Autowired
	BillingService billingService;
	
	@Autowired
	DBLoggingService loggingService;
	
	@Autowired
	MonerisService monerisService;
	
	@Autowired
	CommunicationService communicationService;
	
	public Mono<String> paySingle(PaymentRequest pRequest){
		System.out.println("Orechestrator just received " + pRequest);
		    
	Mono<PaymentRequest> pMono =  Mono.just(pRequest)
	    	      .flatMap(l -> loggingService.updateStatus(l, "CREATED"))
	    	      .flatMap(s -> monerisService.purchase(s))
	    	      .flatMap(l -> loggingService.updateStatus(l, l.getStatus()))
	    	      .flatMap(s -> billingService.updateInformationsMono(s.getId()))
	    	      .map(s -> { 
	    	    	  if (s) {
	    	    		  	loggingService.updateStatus(pRequest, "BILLING_UPDATED");
	    	    		  	return pRequest.updateStatus("BILLING_UPDATED");
	    	    	  	}else {
	    	    	  		loggingService.updateStatus(pRequest, "BILLING_NOT_UPDATED");
	    	    	  		return pRequest.updateStatus("BILLING_NOT_UPDATED");
	    	    	  	}
	    	    	  }
	    	      )
	
//	    	      .map(s ->  communicationService.sendMessage())
	    	      .log();
	
		pMono.block();
	    
	    return  Mono.just("banane");
	    
	    
	    
	    	
//	    	      .onErrorResume(err -> {
//	    	          return Mono.just(order.setOrderStatus(OrderStatus.FAILURE)
//	    	            .setResponseMessage(err.getMessage()));
//	    	      })
//	    	      .flatMap(o -> {
//	    	          if (!OrderStatus.FAILURE.equals(o.getOrderStatus())) {
//	    	              return webClient.method(HttpMethod.POST)
//	    	                .uri(shippingServiceUrl)
//	    	                .body(BodyInserters.fromValue(o))
//	    	                .exchange();
//	    	          } else {
//	    	              return Mono.just(o);
//	    	          }
//	    	      })
//	    	      .onErrorResume(err -> {
//	    	          return webClient.method(HttpMethod.POST)
//	    	            .uri(inventoryServiceUrl)
//	    	            .body(BodyInserters.fromValue(order))
//	    	            .retrieve()
//	    	            .bodyToMono(Order.class)
//	    	            .map(o -> o.setOrderStatus(OrderStatus.FAILURE)
//	    	              .setResponseMessage(err.getMessage()));
//	    	      })
//	    	      .map(o -> {
//	    	          if (!OrderStatus.FAILURE.equals(o.getOrderStatus())) {
//	    	              return order.setShippingDate(o.getShippingDate())
//	    	                .setOrderStatus(OrderStatus.SUCCESS);
//	    	          } else {
//	    	              return order.setOrderStatus(OrderStatus.FAILURE)
//	    	                .setResponseMessage(o.getResponseMessage());
//	    	          }
//	    	      })
//	    	      .flatMap(orderRepository::save);
//	    
//	    
	    
		
	
	}


}
