package github.axgiri.PurchaseService.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import github.axgiri.PurchaseService.Enum.StatusEnum;
import github.axgiri.PurchaseService.Model.Purchase;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {
    
    private Long id;

    private UUID uuid;

    private StatusEnum status;

    @NotNull(message = "policy id is required")
    private Long policyId;

    @NotNull(message = "gov id is required")
    @Pattern(regexp = "\\d{10}", message = "gov id must be exactly 10 digits")
    private String govId;

    private LocalDateTime createdAt;

    public Purchase toEntity(){
        Purchase purchase = new Purchase();
        purchase.setId(id);
        purchase.setUuid(uuid);
        purchase.setStatus(status);
        purchase.setPolicyId(policyId);
        purchase.setGovId(govId);
        purchase.setCreatedAt(createdAt);
        return purchase;
    }

    public static PurchaseDTO fromEntityToDTO(Purchase purchase){
        return new PurchaseDTO(
            purchase.getId(),
            purchase.getUuid(),
            purchase.getStatus(),
            purchase.getPolicyId(),
            purchase.getGovId(),
            purchase.getCreatedAt()
        );
    }
}
