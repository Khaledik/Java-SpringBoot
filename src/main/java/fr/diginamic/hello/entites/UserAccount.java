package fr.diginamic.hello.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();


    public UserAccount(String username, String password, String... authorities) {
        this.username = username;
        this.password = password;
        this.authorities = Arrays.stream(authorities).map(SimpleGrantedAuthority::new).map(GrantedAuthority.class::cast).toList();
    }

    /**
     * Getter for id.
     *
     * @return the value of id.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for username.
     *
     * @return the value of username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for password.
     *
     * @return the value of password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for authorities.
     *
     * @return the value of authorities.
     */
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
