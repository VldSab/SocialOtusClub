package ru.saberullin.socialotusclub.user.service;

import ru.saberullin.socialotusclub.user.model.UserDto;

import java.util.List;

public interface UserService {
    UserDto findUserById(Long id);
    List<UserDto> findUsersByNameAndSurname(String firstName, String lastName);
    void fillSynthetic(Integer amount);
    UserDto updateUser(UserDto userDto);
}
