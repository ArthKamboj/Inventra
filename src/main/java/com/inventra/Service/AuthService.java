package com.inventra.Service;

import com.inventra.Dto.AuthResponse;
import com.inventra.Dto.LoginRequest;
import com.inventra.Dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
