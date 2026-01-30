package Lets_play.Backend.Services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mongodb.DuplicateKeyException;

import Lets_play.Backend.Configs.Jwt.Role;
import Lets_play.Backend.DTO.ErrorResponse;
import Lets_play.Backend.DTO.RegisterDTO;
import Lets_play.Backend.Model.User;
import Lets_play.Backend.Repository.userRepository;

@Service
public class RegisterService {
    private final PasswordEncoder passwordEncoder;
    private final userRepository userRepository;

    public RegisterService(PasswordEncoder passwordEncoder, userRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    public ResponseEntity<?> register(RegisterDTO body) {
        try {
            
            User new_User = new User();
            new_User.setUsername(body.getUsername());
            new_User.setEmail(body.getEmail());
            new_User.setPassword(this.passwordEncoder.encode(body.getPassword()));
            new_User.setRole(Role.User.toString());
            userRepository.save(new_User);
            return ResponseEntity.ok("User Registerd");
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("User Already Exist"));
        }
    }
}
