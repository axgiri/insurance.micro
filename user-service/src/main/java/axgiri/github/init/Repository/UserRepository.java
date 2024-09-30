package axgiri.github.init.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import axgiri.github.init.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByEmail(String email);
}
