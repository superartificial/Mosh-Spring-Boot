package nz.clem.store.services;

import lombok.AllArgsConstructor;
import nz.clem.store.controllers.exceptions.UnauthorizedException;
import nz.clem.store.dtos.LoginRequest;
import nz.clem.store.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRespository;
//    private final PasswordEncoder passwordEncoder;

//    public void login(LoginRequest request) {
//        var user = userRespository.findByEmail(request.getEmail()).orElseThrow(UnauthorizedException::new);
//        System.out.println("User: " + user);
//        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            System.out.println("NOT AUTHORIZED");
//            throw new UnauthorizedException();
//        }
//        System.out.println("Logged in as " + user.getName());
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRespository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + email)
        );
        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }


}
