package poc.vb.springreactive.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CommunicationService {

	public Mono<String> sendMessage() {
		System.out.println("CommunicationService: You send a message");
		return Mono.just("SUCCESS");
	}
}
