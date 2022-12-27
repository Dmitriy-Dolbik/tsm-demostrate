package ru.karod.tsm.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTTokenSuccessResponse {
    private String token;
}
