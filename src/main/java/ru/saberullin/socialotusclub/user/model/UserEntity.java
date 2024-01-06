package ru.saberullin.socialotusclub.user.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    private String surname;
    private Integer age;
    private String sex;
    private String interests;
    private String city;
    @NonNull
    private String password;
}
