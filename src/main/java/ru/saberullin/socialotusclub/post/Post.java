package ru.saberullin.socialotusclub.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class Post {
    private Long id;
    @NotNull
    private Long owner_id;
    @NotNull
    @NotBlank
    private String payload;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("owner_id", owner_id);
        map.put("payload", payload);
        return map;
    }
}
