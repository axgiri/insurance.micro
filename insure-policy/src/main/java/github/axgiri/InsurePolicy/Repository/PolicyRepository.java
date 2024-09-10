package github.axgiri.InsurePolicy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import github.axgiri.InsurePolicy.Model.Policy;

public interface PolicyRepository extends JpaRepository<Policy,Long>{
}
