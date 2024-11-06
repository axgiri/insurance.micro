package axgiri.github.DocumentService.Request;

import lombok.Data;

@Data
public class PolicyResponse {
    
    private Long id;
    private String insurancePackage;
    private String insuranceType;
    private Double price;
}
