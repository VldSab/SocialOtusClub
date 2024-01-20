package ru.saberullin.socialotusclub.user.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    private Long id;
    @NotNull
    private String username;
    private String name;
    private String surname;
    private Integer age;
    private String sex;
    private String interests;
    private String city;
    @NotNull
    private String password;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("name", name);
        map.put("surname", surname);
        map.put("age", age);
        map.put("sex", sex);
        map.put("interests", interests);
        map.put("city", city);
        map.put("password", password);

        return map;
    }
}
