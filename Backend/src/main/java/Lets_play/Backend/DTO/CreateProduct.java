package Lets_play.Backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProduct {
    @NotBlank(message = "Produt name is required")
    @Size(min = 5, max = 20, message = "Product name should be between 5 and 20 letters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "product name shouldn't have speciale characters")
    private String name;

    @NotNull(message = "Product price is required")
    @Positive
    private Double price;
    
    @NotBlank(message = "Produt description is required")
    @Size(min = 15, max = 100, message = "Product description should be between 5 and 20 letters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "product description shouldn't have speciale characters")
    private String description;
}
