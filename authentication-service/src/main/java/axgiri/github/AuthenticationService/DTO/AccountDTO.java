package axgiri.github.AuthenticationService.DTO;

import axgiri.github.AuthenticationService.Model.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    
    @NotNull(message = "role is required")
    private String role;
    
    @Email
    @NotNull(message = "login is required")
    private String email;

    @NotNull(message = "password is required")
    private String password;

    public Account toEntity(){
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        return account;
    }

    public static AccountDTO fromEntityToDTO(Account account){
        return new AccountDTO(
            account.getRole() != null ? account.getRole().name() : null,
            account.getEmail(),
            account.getPassword()
        );
    }
}
