package axgiri.github.init.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import axgiri.github.init.DTO.LoginRequest;
import axgiri.github.init.DTO.UserDTO;
import axgiri.github.init.Model.User;
import axgiri.github.init.Repository.UserRepository;
import axgiri.github.init.Security.AuthResponse;
import axgiri.github.init.Security.TokenService;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService, AuthenticationManager authenticationManager){ 
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
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

    public AuthResponse createUser(UserDTO userDTO) {
        User user = userDTO.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("Creating new user with email: {}", user.getEmail());
        repository.save(user);
        String token = tokenService.generateToken(user);
        return new AuthResponse(token, UserDTO.fromEntityToDTO(user));
    }
    
    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User with email: " + request.getEmail() + " not found"));
        String token = tokenService.generateToken(user);
        return new AuthResponse(token, UserDTO.fromEntityToDTO(user));
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

    public UserDTO getUserByEmail(String email){
        logger.info("fetching user with email: {}", email);
        User user = repository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("user with email" + email + " not found"));
        return UserDTO.fromEntityToDTO(user);
    }
}
