package nz.clem.store.controllers;

import lombok.AllArgsConstructor;
import nz.clem.store.dtos.RegisterUserRequest;
import nz.clem.store.dtos.UserDto;
import nz.clem.store.entities.User;
import nz.clem.store.mappers.UserMapper;
import nz.clem.store.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    // best practice to name parameter even if the same as variable name, in case we decide to change variable name later
    public List<UserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort
    ) {
        if(!Set.of("name", "email").contains(sort))
            sort = "name";
        return userRepository.findAll(Sort.by(sort))
            .stream()
            .map(userMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
         var user = userRepository.findById(id).orElse(null);
         if(user == null) {
             return ResponseEntity.notFound().build();
         }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public RegisterUserRequest createUser(@RequestBody RegisterUserRequest request) {
//        userRepository.save(userMapper.toEntity(userDto));
        var user = userMapper.toEntity(request);
        System.out.println(user);
        return null;
    }

}
