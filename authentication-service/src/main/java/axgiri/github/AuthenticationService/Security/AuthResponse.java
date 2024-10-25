package axgiri.github.AuthenticationService.Security;

import axgiri.github.AuthenticationService.DTO.AccountDTO;
import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private AccountDTO Account;

    public AuthResponse(String token, AccountDTO account) {
        this.token = token;
        this.Account = account;
    }
}
