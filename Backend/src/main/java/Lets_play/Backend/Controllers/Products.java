package Lets_play.Backend.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Lets_play.Backend.DTO.CreateProduct;
import Lets_play.Backend.DTO.ProductsResponse;
import Lets_play.Backend.Services.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class Products {
    private final ProductService productService;

    public Products(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductsResponse>> GetProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetSpecifiqueProduct(@PathVariable String id) {
        return productService.getItem(id);
    }

    @PostMapping
    public ResponseEntity<?> CreateProduct(@Valid @RequestBody CreateProduct dto) {
        return productService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateProduct(@Valid @RequestBody CreateProduct dto, @PathVariable String id) {
        return productService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> RemoveProduct(@PathVariable String id) {
        return productService.delete(id);
    }
}
