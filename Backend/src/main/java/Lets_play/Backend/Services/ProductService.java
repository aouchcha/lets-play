package Lets_play.Backend.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import Lets_play.Backend.DTO.CreateProduct;
import Lets_play.Backend.DTO.ProductsResponse;
import Lets_play.Backend.Model.Product;
import Lets_play.Backend.Repository.ProductRepository;
import Lets_play.Backend.Repository.userRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final userRepository userRepository;

    public ProductService(ProductRepository productRepository, userRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<ProductsResponse>> getProducts() {
        List<Product> products = productRepository.findAll();
        
        List<ProductsResponse> response = new ArrayList<>();

        for (Product product : products) {
            ProductsResponse responseProduct = new ProductsResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()); 
            response.add(responseProduct);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<ProductsResponse> getItem(Long id) {
        Product product = productRepository.findById(id);
        ProductsResponse item = new ProductsResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> create(CreateProduct body) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username =========~" + username);
        return ResponseEntity.ok(null);
    }
}
