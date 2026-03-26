package Lets_play.Backend.Services;

import Lets_play.Backend.Configs.Jwt.Role;
import Lets_play.Backend.DTO.CreateProduct;
import Lets_play.Backend.DTO.ProductsResponse;
import Lets_play.Backend.Model.Product;
import Lets_play.Backend.Model.User;
import Lets_play.Backend.Repository.ProductRepository;
import Lets_play.Backend.Repository.userRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private userRepository userRepository;  

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        // Create a test product before each test
        testProduct = new Product();
        testProduct.setId("123");
        testProduct.setName("Gaming Laptop");
        testProduct.setDescription("High-performance laptop");
        testProduct.setPrice(1299.99);
        testProduct.setUserId("user123");
    }

    @Test
    void getItem_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findById("123")).thenReturn(Optional.of(testProduct));

        // Act
        ResponseEntity<?> response = productService.getItem("123");

        // Assert
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertTrue(response.getBody() instanceof ProductsResponse),
                () -> {
                    ProductsResponse product = (ProductsResponse) response.getBody();
                    assertEquals("123", product.getId());
                    assertEquals("Gaming Laptop", product.getName());
                    assertEquals("High-performance laptop", product.getDescription());
                    assertEquals(1299.99, product.getPrice());
                });

        // Verify the repository was called exactly once
        verify(productRepository, times(1)).findById("123");
    }

    @Test
    void getItem_WhenProductDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        // when(productRepository.findById("999")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = productService.getItem("999");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found", response.getBody());

        verify(productRepository, times(1)).findById("999");
    }

    @Test
    void getItem_WhenIdIsNull_ShouldReturnNotFound() {
        // Arrange
        when(productRepository.findById(null)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = productService.getItem(null);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found", response.getBody());
    }

    @Test
    void createProduct_WhenValidProduct_ShouldSaveAndReturnProduct() {
       Authentication auth = new UsernamePasswordAuthenticationToken(
        "user123",
        null,
        List.of(new SimpleGrantedAuthority("ROLE_USER"))
       );
       SecurityContext ctx = SecurityContextHolder.createEmptyContext();
       ctx.setAuthentication(auth);
       SecurityContextHolder.setContext(ctx);

       CreateProduct dto = new CreateProduct("product1", 10., "product1 is a good product");
       User user = new User("123", "user123", "user@gmail.com", "whatever", Role.User.toString());
       Product productSaved = new Product();
       productSaved.setId("123");
       productSaved.setName(dto.getName());
       productSaved.setPrice(dto.getPrice());
       productSaved.setDescription(dto.getDescription());
       productSaved.setUserId(user.getId());

       when(productRepository.save(any(Product.class))).thenReturn(productSaved);
       when(userRepository.findByUsername(anyString())).thenReturn(user);
       ResponseEntity<?> response = productService.create(dto);

       assertEquals(HttpStatus.CREATED, response.getStatusCode());
       assertNotNull(response.getBody());

       verify(productRepository, times(1)).save(any(Product.class));
       verify(productRepository).save(argThat(p -> p.getUserId().equals(user.getId())));
       verify(productRepository).save(argThat(p -> p.getName().equals(productSaved.getName())));
       verify(productRepository).save(argThat(p -> p.getPrice().equals(productSaved.getPrice())));
       verify(productRepository).save(argThat(p -> p.getDescription().equals(productSaved.getDescription())));

       verify(userRepository).findByUsername(user.getUsername());
    }
}