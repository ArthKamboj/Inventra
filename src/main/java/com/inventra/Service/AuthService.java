package com.inventra.Service;

import com.inventra.Dto.AuthResponse;
import com.inventra.Dto.LoginRequest;
import com.inventra.Dto.RegisterRequest;

import java.util.Date;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void logout(String token, Date expiry);
    boolean isBlacklisted(String token);
}
