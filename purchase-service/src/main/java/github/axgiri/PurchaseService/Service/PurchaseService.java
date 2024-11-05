package github.axgiri.PurchaseService.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.axgiri.PurchaseService.DTO.PurchaseDTO;
import github.axgiri.PurchaseService.Enum.StatusEnum;
import github.axgiri.PurchaseService.Model.Purchase;
import github.axgiri.PurchaseService.Repository.PurchaseRepository;

@Service
public class PurchaseService {
    
    private static final Logger logger = LoggerFactory.getLogger(PurchaseService.class);
    
    private final PurchaseRepository repository;

    @Autowired
    public PurchaseService(PurchaseRepository repository){
        this.repository = repository;
    }

    public List<PurchaseDTO> get(){
        logger.info("fetching all purchases");
        List<Purchase> purchases = repository.findAll();
        return purchases.stream()
            .map(PurchaseDTO::fromEntityToDTO)
            .collect(Collectors.toList());
    }

    public List<PurchaseDTO> getByStatus(StatusEnum status){
        logger.info("fetching all purchases with status: {}", status);
        List<Purchase> purchase = repository.findByStatus(status);
        return purchase.stream()
            .map(PurchaseDTO::fromEntityToDTO)
            .collect(Collectors.toList());
    }

    public PurchaseDTO getByUuid(UUID uuid){
        logger.info("fetching purchase with uuid: {}", uuid);
        Purchase purchase = repository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("purchase with uuid " + uuid + " not found"));
        return PurchaseDTO.fromEntityToDTO(purchase);
    }

    public PurchaseDTO getPurchase(String govId) {
        logger.info("fetching purchase with gov id: {}", govId);
        Purchase purchase = repository.findByGovId(govId)
                .orElseThrow(() -> new RuntimeException("purchase with gov id " + govId + " not found"));
        return PurchaseDTO.fromEntityToDTO(purchase);
    }

    //TODO: getExamplePDF

    //TODO getPurchasePDF
    
    public PurchaseDTO create(PurchaseDTO purchaseDTO){
        logger.info("creating purchase");
        Purchase purchase = purchaseDTO.toEntity();
        repository.save(purchase);
        return PurchaseDTO.fromEntityToDTO(purchase);
    }

    public String close(UUID uuid) {
        logger.info("closing purchase with uuid: {}", uuid);
        Purchase purchase = repository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("purchase with gov id " + uuid + " not found"));
        purchase.setStatus(StatusEnum.INACTIVE);
        repository.save(purchase);
        return "purchase with gov id " + uuid + " closed";
    }
}
