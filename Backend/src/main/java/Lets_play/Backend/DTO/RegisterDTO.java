package Lets_play.Backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank(message = "Username required")
    @Size(min = 5, max = 9, message = "Username should be between 5 and 9 letters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$")
    private String username;

    @NotBlank(message = "Email required")
    @Email(message = "Invalide email format")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 5, max = 15, message = "Password should be between 5 and 15 letters")
    private String password;
}
