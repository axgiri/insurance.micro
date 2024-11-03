package github.axgiri.InsurePolicy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import github.axgiri.InsurePolicy.Model.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy,Long>{
    
}
