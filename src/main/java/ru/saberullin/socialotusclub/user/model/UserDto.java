package ru.saberullin.socialotusclub.user.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserDto {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    private String surname;
    private Integer age;
    private String sex;
    private String interests;
    private String city;
}
