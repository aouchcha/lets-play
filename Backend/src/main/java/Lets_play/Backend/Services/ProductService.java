package Lets_play.Backend.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<?> getProducts() {
        return null;
    }
}
