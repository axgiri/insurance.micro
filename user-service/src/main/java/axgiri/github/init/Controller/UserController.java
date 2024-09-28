package axgiri.github.init.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import axgiri.github.init.DTO.LoginRequest;
import axgiri.github.init.DTO.UserDTO;
import axgiri.github.init.Model.User;
import axgiri.github.init.Security.AuthResponse;
import axgiri.github.init.Service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        UserDTO user = service.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@Valid @RequestBody UserDTO userDTO) {
        AuthResponse authResponse = service.createUser(userDTO);
        return ResponseEntity.ok(authResponse);
        
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        AuthResponse authResponse = service.authenticate(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO){
        User user = service.updateUser(id, userDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteUser(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok("user deleted");
    }

    @PostMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email){
        UserDTO user = service.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}
