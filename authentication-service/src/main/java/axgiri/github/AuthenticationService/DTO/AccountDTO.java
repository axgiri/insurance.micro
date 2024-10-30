package axgiri.github.AuthenticationService.DTO;

import axgiri.github.AuthenticationService.Enum.RoleEnum;
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
    
    private Long id;

    @NotNull(message = "role is required")
    private RoleEnum role;
    
    @Email
    @NotNull(message = "email is required")
    private String email;

    @NotNull(message = "password is required")
    private String password;

    public Account toEntity() {
        Account account = new Account();
        account.setId(id);
        account.setRole((role == null) ? RoleEnum.MODERATOR : role);
        account.setEmail(email);
        account.setPassword(password);
        return account;
    }

    public static AccountDTO fromEntityToDTO(Account account) {
        return new AccountDTO(
            account.getId(),
            account.getRole(),
            account.getEmail(),
            account.getPassword()
        );
    }
}
