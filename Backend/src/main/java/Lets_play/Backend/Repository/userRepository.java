package Lets_play.Backend.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import Lets_play.Backend.Model.User;

@Repository
public interface userRepository extends MongoRepository<User, String> {
    
}
