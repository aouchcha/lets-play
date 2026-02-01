package Lets_play.Backend.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import Lets_play.Backend.Configs.Jwt.Role;
import Lets_play.Backend.DTO.CreateProduct;
import Lets_play.Backend.DTO.ProductsResponse;
import Lets_play.Backend.Model.Product;
import Lets_play.Backend.Model.User;
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

    public ResponseEntity<?> getItem(String id) {
        final Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        ProductsResponse item = new ProductsResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> create(CreateProduct body) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You don't have permission to create a product");
        }
        Product product = new Product();
        product.setName(body.getName());
        product.setDescription(body.getDescription());
        product.setPrice(body.getPrice());
        product.setUserId(user.getId());
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product Created");
    }

    public ResponseEntity<?> update(CreateProduct body, String id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = userRepository.findByUsername(username);
        if (user == null || (!user.getRole().equals(Role.Admin.toString()) && !user.getId().equals(product.getUserId()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You don't have permission to create a product");
        }
        product.setName(body.getName());
        product.setDescription(body.getDescription());
        product.setPrice(body.getPrice());
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.OK).body("Product Updated");
    }

    public ResponseEntity<?> delete(String id) {
        final Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = userRepository.findByUsername(username);
        if (user == null || (!user.getRole().equals(Role.Admin.toString()) && !user.getId().equals(product.getUserId()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You don't have permission to create a product");
        }
        productRepository.delete(product);
        return ResponseEntity.status(HttpStatus.OK).body("Product Removed");

    }
}
