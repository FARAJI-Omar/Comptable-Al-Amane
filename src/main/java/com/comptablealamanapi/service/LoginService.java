package com.comptablealamanapi.service;

import com.comptablealamanapi.dto.request.LoginRequest;
import com.comptablealamanapi.dto.response.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginRequest request);
}