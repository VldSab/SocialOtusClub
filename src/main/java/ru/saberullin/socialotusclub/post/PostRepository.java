package ru.saberullin.socialotusclub.post;

public interface PostRepository {
    Post savePost(Post post);
    boolean existsById(Long id);
}
