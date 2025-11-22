package com.comptablealamanapi.dto;

import com.comptablealamanapi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String email;
    private String nomComplet;
    private Role role;
    private Long societeId;
}
