package Lets_play.Backend.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        return ResponseEntity.ok(response);
    }
}
