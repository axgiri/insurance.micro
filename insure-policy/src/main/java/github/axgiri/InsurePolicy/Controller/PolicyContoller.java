package github.axgiri.InsurePolicy.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.axgiri.InsurePolicy.DTO.PolicyDTO;
import github.axgiri.InsurePolicy.Service.PolicyService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/policy")
public class PolicyContoller {
    
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PolicyService.class);

    private final PolicyService service;

    @Autowired
    public PolicyContoller(PolicyService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PolicyDTO>> getPolicies() {
        logger.info("request to fetch all policies");
        List<PolicyDTO> policies = service.getPolicies();
        return ResponseEntity.ok(policies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyDTO> getPolicyById(@PathVariable Long id){
        logger.info("request to fetch policy with id {}", id);
        PolicyDTO policyDTO = service.getPolicy(id);
        return ResponseEntity.ok(policyDTO);
    }

    @PostMapping("/admin")
    public ResponseEntity<PolicyDTO> createPolicy(@RequestBody @Valid PolicyDTO policyDTO){
        logger.info("reques to create new policy");
        PolicyDTO createdPolicy = service.createPolicy(policyDTO);
        return ResponseEntity.ok(createdPolicy);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<PolicyDTO> updatePolicy(@PathVariable Long id, @RequestBody @Valid PolicyDTO policyDTO){
        logger.info("request to update policy with id {}", id);
        PolicyDTO updatedPolicy = service.updatePolicy(id, policyDTO);
        return ResponseEntity.ok(updatedPolicy);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id){
        logger.info("request to delete policy with id {}", id);
        service.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}
