package ru.saberullin.socialotusclub.user.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
