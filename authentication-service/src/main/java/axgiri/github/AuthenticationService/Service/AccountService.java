package axgiri.github.AuthenticationService.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import axgiri.github.AuthenticationService.DTO.AccountDTO;
import axgiri.github.AuthenticationService.Enum.RoleEnum;
import axgiri.github.AuthenticationService.Model.Account;
import axgiri.github.AuthenticationService.Repository.AccountRepository;
import axgiri.github.AuthenticationService.Request.LoginRequest;
import axgiri.github.AuthenticationService.Security.AuthResponse;
import axgiri.github.AuthenticationService.Security.TokenService;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AccountService(AccountRepository repository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public List<AccountDTO> get(){
        logger.info("fetching all account");
        List<Account> accounts = repository.findAll();
        return accounts.stream()
                .map(AccountDTO::fromEntityToDTO)
                .collect(Collectors.toList());
    }

    public AccountDTO getById(Long id){
        logger.info("fetching policy with id: {}", id);
        Account accounts = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("user with id: " + id + " not found"));
            logger.error("Policy with ID: {} not found", id);
        return AccountDTO.fromEntityToDTO(accounts);
    }

    public List<AccountDTO> getByRole(RoleEnum role){
        List<Account> accounts = repository.findByRole(role);
        return accounts.stream()
                .map(AccountDTO::fromEntityToDTO)
                .toList();
    }

    public AccountDTO update(Long id, AccountDTO accountDTO){
        AccountDTO existingAccountDTO = getById(id);
        Account account = existingAccountDTO.toEntity();
        account.setEmail(accountDTO.getEmail());
        account.setPassword(accountDTO.getPassword());
        return AccountDTO.fromEntityToDTO(account);
    }

    public AccountDTO create(AccountDTO accountDTO){
        logger.info("creating account with data {}", accountDTO);
        Account account = accountDTO.toEntity();
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        repository.save(account);
        return AccountDTO.fromEntityToDTO(account);
    }

    public AuthResponse authenticate(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User with email: " + request.getEmail() + " not found"));
        String token = tokenService.generateToken(user);
        return new AuthResponse(token, AccountDTO.fromEntityToDTO(user));
    }

    public void delete (Long id){
        logger.info("deleting account with id {}", id);
        repository.deleteById(id);
    }
}
