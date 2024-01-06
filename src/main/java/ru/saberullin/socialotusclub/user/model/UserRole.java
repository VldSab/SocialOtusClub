package ru.saberullin.socialotusclub.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UserRole {
    @NonNull
    @JsonProperty("user_id")
    private Long userId;

    @NonNull
    @JsonProperty("role_id")
    private Long roleId;
}
