package ru.saberullin.socialotusclub.user.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
