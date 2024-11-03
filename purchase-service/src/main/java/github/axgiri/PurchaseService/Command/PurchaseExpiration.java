package github.axgiri.PurchaseService.Command;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import github.axgiri.PurchaseService.Enum.StatusEnum;
import github.axgiri.PurchaseService.Model.Purchase;
import github.axgiri.PurchaseService.Repository.PurchaseRepository;

@Service
public class PurchaseExpiration {
    
    private final PurchaseRepository repository;

    @Autowired
    public PurchaseExpiration(PurchaseRepository repository){
        this.repository = repository;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void updatePurchaseStatuses(){
        LocalDateTime now = LocalDateTime.now();

        List<Purchase> active = repository.findByStatus(StatusEnum.ACTIVE);

        for(Purchase purchase : active){
            if (ChronoUnit.YEARS.between(purchase.getCreatedAt(), now) >= 1){
                purchase.setStatus(StatusEnum.INACTIVE);
                repository.save(purchase);
            }
        }
    }
}
