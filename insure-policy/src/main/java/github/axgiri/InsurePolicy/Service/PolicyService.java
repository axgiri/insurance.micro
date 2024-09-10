package github.axgiri.InsurePolicy.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.axgiri.InsurePolicy.DTO.PolicyDTO;
import github.axgiri.InsurePolicy.Model.Policy;
import github.axgiri.InsurePolicy.Repository.PolicyRepository;

@Service
public class PolicyService {
    private static final Logger logger = LoggerFactory.getLogger(PolicyService.class);
    
    private final PolicyRepository repository;

    @Autowired
    public PolicyService (PolicyRepository repository){
        this.repository = repository;
    }

    public List<PolicyDTO> getPolicies(){
        logger.info("fetching all policies");
        List<Policy> policies = repository.findAll();
        return policies.stream()
            .map(PolicyDTO::fromEntityToDTO)
            .collect(Collectors.toList());
    }

    public PolicyDTO getPolicy(Long id){
        logger.info("fetching policy with id: {}", id);
        Policy policy = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("user with id: " + id + " not found"));
            logger.error("Policy with ID: {} not found", id);
        return PolicyDTO.fromEntityToDTO(policy);
    } 

    public PolicyDTO createPolicy(PolicyDTO policyDTO){
        logger.info("creating policy with type: {}, and package {}",
            policyDTO.getInsuranceType(), policyDTO.getInsurancePackage());
        Policy policy = repository.save(policyDTO.toEntity());
        return PolicyDTO.fromEntityToDTO(policy);
    }

    public PolicyDTO updatePolicy(Long id, PolicyDTO policyDTO){
        logger.info("updating policy with id: {}", id);
        Policy policy = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("policy with id " + id + "not found"));
        policy.setInsuransePackage(policyDTO.getInsurancePackage());
        policy.setInsuranceType(policyDTO.getInsuranceType());
        policy.setPrice(policyDTO.getPrice());
        repository.save(policy);
        return PolicyDTO.fromEntityToDTO(policy);
    }

    public void deletePolicy(Long id){
        repository.findById(id)
            .orElseThrow(() -> new RuntimeException("policy with id" + id + "not found"));
        logger.info("deleting policy with id {}", id);
        repository.deleteById(id);
    }
}
