package ru.saberullin.socialotusclub.user.service;

import ru.saberullin.socialotusclub.post.Post;
import ru.saberullin.socialotusclub.user.model.UserDto;

import java.util.List;

public interface UserService {
    UserDto findUserById(Long id);
    List<UserDto> findUsersByNameAndSurname(String firstName, String lastName);
    void fillSynthetic(Integer amount);
    UserDto updateUser(UserDto userDto);
    void addFriend(Long userId, Long friendId);
    Post createPost(Post post);
    List<Post> getUserFeed(Long userId);
}
