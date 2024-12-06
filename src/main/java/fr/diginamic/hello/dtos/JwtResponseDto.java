package fr.diginamic.hello.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDto {
    private String token;

    /**
     * Getter for token.
     *
     * @return the value of token.
     */
    public String getToken() {
        return token;
    }
}
