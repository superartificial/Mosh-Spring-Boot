package nz.clem.store.controllers;

import lombok.AllArgsConstructor;
import nz.clem.store.dtos.UserDto;
import nz.clem.store.mappers.UserMapper;
import nz.clem.store.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(
                        user -> new UserDto(user.getId(), user.getName(), user.getEmail(), LocalDateTime.now())
//                        userMapper::toDto
                ).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
         var user = userRepository.findById(id).orElse(null);
         if(user == null) {
             return ResponseEntity.notFound().build();
         }
         var userDto = new UserDto(user.getId(), user.getName(), user.getEmail(), LocalDateTime.now());
//        return ResponseEntity.ok(userMapper.toDto(user));
        return ResponseEntity.ok(userDto);
    }

}
