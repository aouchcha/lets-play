package Lets_play.Backend.Repository;

import org.springframework.stereotype.Repository;

import Lets_play.Backend.Model.Product;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Product findById(Long id);
    List<Product> findAll();
}
