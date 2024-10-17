package axgiri.github.init.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import axgiri.github.init.DTO.UserDTO;
import axgiri.github.init.Model.User;
import axgiri.github.init.Repository.UserRepository;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public List<UserDTO> getAllUsers(){
        logger.info("fetching all users");
        List<User> policies = repository.findAll();
        return policies.stream()
            .map(UserDTO::fromEntityToDTO)
            .collect(Collectors.toList());
    }

    public UserDTO getUser(Long id){
        logger.info("fetching user with id: {}", id);
        User user = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("user with id:" + id + " not found"));
        return UserDTO.fromEntityToDTO(user);
    }

    public User createUser(UserDTO userDTO){
        User user = userDTO.toEntity();
        logger.info("creating new user with email: {}", user.getEmail());
        return repository.save(user);
    }

    public User updateUser(Long id, UserDTO userDTO){
        User user = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("user with id " + id + "not found"));
        logger.info("updating user with id: {}", id);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());
        return repository.save(user);
    }

    public boolean delete(Long id){
        repository.findById(id)
            .orElseThrow(() -> new RuntimeException("user with id " + id + "not found"));
        logger.info("deleting user with id {}", id);
        repository.deleteById(id);
        return true;
    }
}