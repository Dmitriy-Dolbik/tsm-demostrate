package ru.karod.tsm.security.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private String message;
    public InvalidLoginResponse() {
        this.message = "Incorrect credentials";
    }

}
