package axgiri.github.DocumentService.Service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import axgiri.github.DocumentService.Request.PolicyResponse;

@Service
public class PolicyService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PolicyService.class);
    private final PolicyRequestProducer policyRequestProducer;
    private final PolicyResponseConsumer policyResponseConsumer;

    @Autowired
    public PolicyService(PolicyRequestProducer policyRequestProducer, PolicyResponseConsumer policyResponseConsumer) {
        this.policyRequestProducer = policyRequestProducer;
        this.policyResponseConsumer = policyResponseConsumer;
    }

    public PolicyResponse getPolicyDetails(Long policyId) {
        logger.info("sending a request for a PolicyID to Kafka {}", policyId);
        policyRequestProducer.sendPolicyRequest(policyId);

        PolicyResponse response = policyResponseConsumer.getLatestResponse();
        if (response == null || !response.getId().equals(policyId)) {
            logger.info("response was not received or the policy id does not match");
            return null;
        }
        return response;
    }
}
