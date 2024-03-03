package ru.saberullin.socialotusclub.post;

import com.google.gson.Gson;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class Post {
    private static final Gson gson = new Gson();
    private Long id;
    @NotNull
    private Long ownerId;
    @NotNull
    @NotBlank
    private String payload;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("owner_id", ownerId);
        map.put("payload", payload);
        return map;
    }

    public String toJSON() {
        return gson.toJson(this);
    }

    public static Post fromJSON(String post) {
        return gson.fromJson(post, Post.class);
    }
}
