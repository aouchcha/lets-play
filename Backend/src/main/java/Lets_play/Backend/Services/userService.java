package Lets_play.Backend.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import Lets_play.Backend.DTO.UserDTO;
import Lets_play.Backend.Model.User;
import Lets_play.Backend.Repository.userRepository;

@Service
public class userService {
    private final userRepository userRepository;

    public userService(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> users() {
        final List<User> users = userRepository.findAll();
        List<UserDTO> response = new ArrayList<>();
        for (User u : users) {
            UserDTO dto = new UserDTO(u.getId(), u.getUsername(), u.getEmail(), u.getRole());
            response.add(dto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getSpecificUser(String id) {
        final User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }
        UserDTO response = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
