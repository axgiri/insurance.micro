package github.axgiri.InsurePolicy.Service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import github.axgiri.InsurePolicy.DTO.PolicyDTO;

@Service
public class PolicyRequestConsumer {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PolicyRequestConsumer.class);
    private final PolicyService policyService;
    private final PolicyResponseProducer responseProducer;

    @Autowired
    public PolicyRequestConsumer(PolicyService policyService, PolicyResponseProducer responseProducer) {
        this.policyService = policyService;
        this.responseProducer = responseProducer;
    }

    @KafkaListener(topics = "policy-requests", groupId = "policy-service-group")
    public void consumePolicyRequest(String policyId) {
        logger.info("request for a policy with an id has been received {}", policyId);
        try {
            PolicyDTO policyDTO = policyService.getPolicy(Long.parseLong(policyId));
            responseProducer.sendPolicyResponse(policyDTO);
        } catch (Exception e) {
            logger.error("error processing policyId: " + policyId + ", " + e.getMessage());
        }
    }
}
