package axgiri.github.DocumentService.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import axgiri.github.DocumentService.Request.PolicyResponse;

@Service
public class PolicyResponseConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PolicyResponseConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private PolicyResponse latestResponse;

    @KafkaListener(topics = "policy-responses", groupId = "document-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumePolicyResponse(String jsonResponse) {
        try {
            PolicyResponse response = objectMapper.readValue(jsonResponse, PolicyResponse.class);
            this.latestResponse = response;
            logger.info("policyResponse received and deserialized: {}", response);
        } catch (Exception e) {
            logger.error("failed to deserialize json response: {}", jsonResponse, e);
        }
    }

    public PolicyResponse getLatestResponse() {
        return latestResponse;
    }
}
