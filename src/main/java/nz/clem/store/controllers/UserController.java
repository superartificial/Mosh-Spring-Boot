package nz.clem.store.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nz.clem.store.dtos.ChangePasswordRequest;
import nz.clem.store.dtos.RegisterUserRequest;
import nz.clem.store.dtos.UpdateUserRequest;
import nz.clem.store.dtos.UserDto;
import nz.clem.store.entities.User;
import nz.clem.store.mappers.UserMapper;
import nz.clem.store.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // Using a Response Entity with created means we get a 201 status code, which is best practice REST convention (also uri of saved object)
    @PostMapping
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder
    ) {

        if(userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(
                    Map.of("email","Email is already registered")
            );
        }

        var user = userMapper.toEntity(request);
        var savedUser = userRepository.save(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(uri).body(userMapper.toDto(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request
    ) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.update(request, user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null) {
             return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> ChangePassword(
            @PathVariable(name = "id") Long id,
            @RequestBody ChangePasswordRequest request
    ) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        if(!user.getPassword().equals(request.getOldPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }

}
