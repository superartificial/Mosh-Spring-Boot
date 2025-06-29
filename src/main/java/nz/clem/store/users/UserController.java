package nz.clem.store.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nz.clem.store.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userServiceImpl;
    private final UserService userService;

    @GetMapping
    // best practice to name parameter even if the same as variable name, in case we decide to change variable name later
    public List<UserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort
    ) {
        return userService.getAllUsers(sort);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Using a Response Entity with created means we get a 201 status code, which is best practice REST convention (also uri of saved object)
    @PostMapping
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        System.out.println("REGISTER USER");
        var user = userService.register(request);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userMapper.toDto(user));
    }

    @PutMapping("/{id}")
    public UserDto updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request
    ) {
        return userService.updateUser(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> ChangePassword(
            @PathVariable(name = "id") Long id,
            @RequestBody ChangePasswordRequest request
    ) {
        userService.changePassword(id,request);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("User not found"));
    }

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<ErrorDto> handleExistingUserException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("User already exists"));
    }

    @ExceptionHandler(IncorrectPreviousPasswordException.class)
    public ResponseEntity<ErrorDto> handleIncorrectPreviousPasswordException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto("Incorrect previous password"));
    }

}
