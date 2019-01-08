package ru.geekbrains.pocket.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.pocket.backend.validation.ValidEmail;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

@Entity
@Getter
@Setter
//@Validated
@NoArgsConstructor
@Table(schema = "pocket", name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @NotNull
    @Size(min = 1, max = 50)
    //@Pattern(regexp = "[^0-9]*")
    @Column(name = "lastname", nullable = false)
    private String lastname;

    @NotNull
    @Size(min = 1, max = 50)
    //@Pattern(regexp = "[^0-9]*")
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @NotEmpty
    @ValidEmail
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User(String username, String password, @NotNull @Size(min = 1, max = 50) @Pattern(regexp = "[^0-9]*") String lastname, @NotNull @Size(min = 1, max = 50) @Pattern(regexp = "[^0-9]*") String firstname, @NotEmpty @Email String email) {
        this.username = username;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
    }

    public User(String username, String password, @NotNull @Size(min = 1, max = 50) @Pattern(regexp = "[^0-9]*") String lastname, @NotNull @Size(min = 1, max = 50) @Pattern(regexp = "[^0-9]*") String firstname, @NotEmpty @Email String email, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username +
                ", lastname=" + lastname +
                ", firstname=" + firstname +
                ", email=" + email +
                '}';
    }
}
