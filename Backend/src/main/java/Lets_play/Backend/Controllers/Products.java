package Lets_play.Backend.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Lets_play.Backend.DTO.CreateProduct;
import Lets_play.Backend.DTO.ProductsResponse;
import Lets_play.Backend.Services.ProductService;
import Lets_play.Backend.Services.userService;
import jakarta.validation.Valid;

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
    public ResponseEntity<List<ProductsResponse>> GetProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsResponse> GetSpecifiqueProduct(@PathVariable Long id) {
        return productService.getItem(id);
    }

    @PostMapping
    public ResponseEntity<?> CreateProduct(@Valid @RequestBody CreateProduct dto) {
        return productService.create(dto);
    }
}
