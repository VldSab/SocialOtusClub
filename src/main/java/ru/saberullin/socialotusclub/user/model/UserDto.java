package ru.saberullin.socialotusclub.user.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserDto {
    private Long id;
    @NotNull
    private String name;
    private String surname;
    private Integer age;
    private String sex;
    private String interests;
    private String city;
}
