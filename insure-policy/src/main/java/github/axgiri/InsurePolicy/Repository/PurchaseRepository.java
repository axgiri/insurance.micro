package github.axgiri.InsurePolicy.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import github.axgiri.InsurePolicy.Model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

    Optional<Purchase> getPurchaseById(Long id);
}
