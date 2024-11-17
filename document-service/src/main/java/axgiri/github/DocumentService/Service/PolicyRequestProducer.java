package axgiri.github.DocumentService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PolicyRequestProducer {

    private static final String TOPIC = "policy-requests";
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public PolicyRequestProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPolicyRequest(Long policyId) {
        kafkaTemplate.send(TOPIC, policyId.toString());
    }
}
