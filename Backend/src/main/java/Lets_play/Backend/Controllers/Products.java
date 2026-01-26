package Lets_play.Backend.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Lets_play.Backend.Services.ProductService;
import Lets_play.Backend.Services.userService;

@RestController
@RequestMapping("/api/products")
public class Products {
    private final ProductService productService;
    private final userService userService;

    public Products(ProductService productService, userService userService) {
        this.productService = productService;
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<?> GetProducts() {
        return null;
    }
}
