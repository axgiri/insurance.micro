package axgiri.github.init.Model;

import axgiri.github.init.Enum.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String password;

    public User(String name, String email, Integer age, String password, RoleEnum roleEnum) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
        this.roleEnum = roleEnum;
    }

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;
}
