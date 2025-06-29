package nz.clem.store.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public List<UserDto> getAllUsers(String sort) {
        if(!Set.of("name", "email").contains(sort))
            sort = "name";
        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto).toList();
    }

    public UserDto getUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }

    public nz.clem.store.users.User register(@Valid RegisterUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) throw new ExistingUserException();
        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public UserDto updateUser(Long id, UpdateUserRequest request) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userMapper.update(request, user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    public void changePassword(Long id, ChangePasswordRequest request) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if(!user.getPassword().equals(request.getOldPassword())) throw new IncorrectPreviousPasswordException();
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }

}
