package com.inventra.Service.ServiceImplementation;

import com.inventra.Dto.AuthResponse;
import com.inventra.Dto.LoginRequest;
import com.inventra.Dto.RegisterRequest;
import com.inventra.Models.BlackListedToken;
import com.inventra.Models.Role;
import com.inventra.Models.User;
import com.inventra.Repository.BlackListedTokenRespository;
import com.inventra.Repository.UserRepository;
import com.inventra.Service.AuthService;
import com.inventra.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BlackListedTokenRespository blackListedTokenRespository;

    public AuthServiceImplementation(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager,
                                     BlackListedTokenRespository blackListedTokenRespository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.blackListedTokenRespository = blackListedTokenRespository;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user= userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        return response;
    }

    @Override
    public void logout(String token, Date expiry) {
        BlackListedToken blackListedToken = new BlackListedToken();
        blackListedToken.setToken(token);
        blackListedToken.setExpiryDate(expiry.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blackListedTokenRespository.existsByToken(token);
    }
}
