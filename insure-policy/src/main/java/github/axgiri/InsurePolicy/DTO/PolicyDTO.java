package github.axgiri.InsurePolicy.DTO;

import github.axgiri.InsurePolicy.Enum.PackageEnum;
import github.axgiri.InsurePolicy.Enum.TypeEnum;
import github.axgiri.InsurePolicy.Model.Policy;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyDTO {
    
    @NotNull(message = "Insurance package is required")
    private PackageEnum insurancePackage;

    @NotNull(message = "Insurance type is required")
    private TypeEnum insuranceType;

    @NotNull(message = "Price is required")
    private Float price;

    public Policy toEntity(){
        Policy policy = new Policy();
        policy.setInsuranceType(this.insuranceType);
        policy.setInsuransePackage(insurancePackage);
        policy.setPrice(this.price);
        return policy;
    }

    public static PolicyDTO fromEntityToDTO(Policy policy){
        return new PolicyDTO(
            policy.getInsuransePackage(), 
            policy.getInsuranceType(), 
            policy.getPrice()
        );
    }
}
