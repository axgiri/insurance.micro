package axgiri.github.DocumentService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import axgiri.github.DocumentService.Request.PolicyResponse;

@Service
public class PolicyService {

    private final WebClient webClient;

    @Autowired
    public PolicyService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://host.docker.internal:8080/api/policy").build();
    }

    public PolicyResponse getPolicyDetails(Long policyId) {
        return webClient.get()
                .uri("/public/{id}", policyId)
                .retrieve()
                .bodyToMono(PolicyResponse.class)
                .block();
    }
}