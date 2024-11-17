package github.axgiri.InsurePolicy.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import github.axgiri.InsurePolicy.DTO.PolicyDTO;

@Service
public class PolicyResponseProducer {

    private static final Logger logger = LoggerFactory.getLogger(PolicyResponseProducer.class);
    private static final String TOPIC = "policy-responses";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public PolicyResponseProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void sendPolicyResponse(PolicyDTO response) {
        try {
            String jsonResponse = objectMapper.writeValueAsString(response);
            kafkaTemplate.send(TOPIC, jsonResponse);
            logger.info("json response has been sent to Kafka: {}", jsonResponse);
        } catch (JsonProcessingException e) {
            logger.error("failed to serialize PolicyDTO to json", e);
        }
    }
}
