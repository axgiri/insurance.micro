package github.axgiri.PurchaseService.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import github.axgiri.PurchaseService.Enum.StatusEnum;
import github.axgiri.PurchaseService.Model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

    Optional<Purchase> findByGovId(String govId);

    Optional<Purchase> findByUuid(UUID uuid);

    List<Purchase> findByStatus(StatusEnum status);
}
