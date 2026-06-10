package ra.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.demo.model.dto.request.RefreshTokenRequest;
import ra.demo.model.dto.request.UserLogin;
import ra.demo.model.dto.request.UserRegister;
import ra.demo.model.dto.response.JWTResponse;
import ra.demo.model.entity.RefreshToken;
import ra.demo.model.entity.Users;
import ra.demo.repository.RefreshTokenRepository;
import ra.demo.repository.UserRepository;
import ra.demo.security.jwt.JWTProvider;
import ra.demo.security.principal.CustomUserDetails;
import ra.demo.service.RefreshTokenService;
import ra.demo.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsService userDetailsService;

    @Override
    public Users registerUser(UserRegister userRegister) {
        Users users = Users.builder()
                .username(userRegister.getUsername())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .fullName(userRegister.getFullName())
                .email(userRegister.getEmail())
                .phone(userRegister.getPhone())
                .enabled(true)
                .roles(userRegister.getRoles())
                .build();
        return userRepository.save(users);
    }

    @Override
    public JWTResponse login(UserLogin userLogin) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtProvider.generateToken(userDetails.getUsername());

            String refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername()).getRefreshToken();

            return JWTResponse.builder()
                    .username(userDetails.getUsername())
                    .password(userDetails.getPassword())
                    .fullName(userDetails.getFullName())
                    .email(userDetails.getEmail())
                    .enabled(userDetails.getEnabled())
                    .authorities(userDetails.getAuthorities())
                    .token(token)
                    .refreshToken(refreshToken)
                    .build();
        } catch (AuthenticationException e) {
            log.info("Sai username hoặc password!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public JWTResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken()).orElseThrow(()-> new RuntimeException("Refresh token không tồn tại"));

        refreshTokenService.verifyExpiration(refreshToken);

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(refreshToken.getUsername());
        String token = jwtProvider.generateToken(userDetails.getUsername());

        return JWTResponse.builder()
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .fullName(userDetails.getFullName())
                .email(userDetails.getEmail())
                .enabled(userDetails.getEnabled())
                .authorities(userDetails.getAuthorities())
                .token(token)
                .build();
    }
}
