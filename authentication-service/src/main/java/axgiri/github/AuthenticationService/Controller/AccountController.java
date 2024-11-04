package axgiri.github.AuthenticationService.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<List<AccountDTO>> get(){
        logger.info("request to fetch all accounts");
        List<AccountDTO> accounts = service.get();
        return ResponseEntity.ok(accounts);
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @GetMapping("/admin/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable Long id){
        logger.info("request to fetch account with id {}", id);
        AccountDTO accountDTO = service.getById(id);
        return ResponseEntity.ok(accountDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/getByRole")
    public ResponseEntity<List<AccountDTO>> getByRole(@RequestParam RoleEnum role){
        List<AccountDTO> accounts = service.getByRole(role);
        return ResponseEntity.ok(accounts);
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @PutMapping("/admin/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Long id, @RequestBody @Valid AccountDTO accountDTO){
        logger.info("request to update account with id {}", id);
        AccountDTO updatedAccount = service.update(id, accountDTO);
        return ResponseEntity.ok(updatedAccount);
    }

    @PostMapping("/public/register")
    public ResponseEntity<AuthResponse> create(@RequestBody @Valid AccountDTO accountDTO){
        logger.info("request to create new account");
        AuthResponse authResponse = service.create(accountDTO);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/public/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        logger.info("request to authenticate user with email {}", loginRequest.getEmail());
        AuthResponse authResponse = service.authenticate(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        logger.info("request to delete account with id {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public/validate")
    public ResponseEntity<String> validate(@RequestHeader("Authorization") String token){
        logger.info("request to validate token: {}", token);
        token = token.substring(7);
        service.validateToken(token);
        return ResponseEntity.ok("validation successful");
    }
}
