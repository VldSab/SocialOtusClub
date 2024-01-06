package ru.saberullin.socialotusclub.role.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @NonNull
    private Long id;
    @NonNull
    private String role;
}
