package axgiri.github.init.DTO;

import org.springframework.lang.Nullable;

import axgiri.github.init.Enum.RoleEnum;
import axgiri.github.init.Model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "name is required")
    private String name;

    @Email
    @NotBlank(message = "email is required")
    private String email;

    @NotNull(message = "age is required")
    @Positive(message = "age is cannot be negative number")
    private Integer age;

    @NotNull(message = "age is required")    
    private String password;

    @Nullable
    private RoleEnum role;

    public static UserDTO fromEntityToDTO(User user) {
        return new UserDTO(user.getName(), user.getEmail(), user.getAge(), user.getPassword(), user.getRoleEnum());
    }

    public User toEntity() {
        RoleEnum userRole = (role == null) ? RoleEnum.USER:role;
        return new User(name, email, age, password, userRole);
    }
}
