package fr.diginamic.hello.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDto {
    private String username;
    private String password;

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
}
