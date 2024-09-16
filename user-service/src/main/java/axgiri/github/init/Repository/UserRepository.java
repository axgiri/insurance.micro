package axgiri.github.init.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import axgiri.github.init.Model.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByEmail(String email);
}
