package axgiri.github.AuthenticationService.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import axgiri.github.AuthenticationService.DTO.AccountDTO;
import axgiri.github.AuthenticationService.Enum.RoleEnum;
import axgiri.github.AuthenticationService.Request.LoginRequest;
import axgiri.github.AuthenticationService.Security.AuthResponse;
import axgiri.github.AuthenticationService.Service.AccountService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountService.class);

    private final AccountService service; 

    public AccountController(AccountService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> get(){
        logger.info("request to fetch all accounts");
        List<AccountDTO> accounts = service.get();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable Long id){
        logger.info("request to fetch account with id {}", id);
        AccountDTO accountDTO = service.getById(id);
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/getByRole")
    public ResponseEntity<List<AccountDTO>> getByRole(@RequestParam RoleEnum role){
        List<AccountDTO> accounts = service.getByRole(role);
        return ResponseEntity.ok(accounts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Long id, @RequestBody @Valid AccountDTO accountDTO){
        logger.info("request to update account with id {}", id);
        AccountDTO updatedAccount = service.update(id, accountDTO);
        return ResponseEntity.ok(updatedAccount);
    }

    @PostMapping("/register")
    public ResponseEntity<AccountDTO> create(@RequestBody @Valid AccountDTO accountDTO){
    logger.info("request to create new account");
        AccountDTO createdAccount = service.create(accountDTO);
        return ResponseEntity.ok(createdAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        AuthResponse authResponse = service.authenticate(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        logger.info("request to delete account with id {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping("/validate")
}
