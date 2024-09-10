package axgiri.github.init.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import axgiri.github.init.Model.User;

public interface UserRepository extends JpaRepository<User,Long>{
}
