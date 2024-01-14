package ru.saberullin.socialotusclub.user.service;

import ru.saberullin.socialotusclub.user.model.UserDto;

public interface UserService {
    UserDto findUserById(Long id);
}
