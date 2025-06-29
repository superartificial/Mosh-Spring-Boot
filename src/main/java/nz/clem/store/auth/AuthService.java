package nz.clem.store.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import nz.clem.store.users.User;
import nz.clem.store.users.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long)authentication.getPrincipal();
        return userRepository.findById(userId).orElse(null);
    }

    public JwtResponse loginUser(LoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var accessToken = jwtService.generateAccessToken(user);

        var refreshToken = jwtService.generateRefreshToken(user);
        var cookie = new Cookie("refreshToken", refreshToken.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);
        return new JwtResponse(accessToken.toString());
    }

    public JwtResponse refreshToken(String refreshToken) {
        var jwt = jwtService.parseToken(refreshToken);
        if(jwt == null || jwt.isExpired()) {
            throw new UnauthorizedException();
        }
        var user = userRepository.findById(jwt.getUserId()).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);
        return new JwtResponse(accessToken.toString());
    }



}
