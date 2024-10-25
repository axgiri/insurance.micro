package axgiri.github.AuthenticationService.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import axgiri.github.AuthenticationService.Enum.RoleEnum;
import axgiri.github.AuthenticationService.Model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByEmail(String email);

    List<Account> findByRole(RoleEnum role);
}
