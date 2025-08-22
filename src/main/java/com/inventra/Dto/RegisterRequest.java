package com.inventra.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String role;
}
