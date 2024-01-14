package ru.saberullin.socialotusclub.user.service;

import org.springframework.stereotype.Service;
import ru.saberullin.socialotusclub.user.UserNotFoundException;
import ru.saberullin.socialotusclub.user.model.UserDto;
import ru.saberullin.socialotusclub.user.model.UserEntity;
import ru.saberullin.socialotusclub.user.repository.UserRepository;

import java.util.Optional;

import static ru.saberullin.socialotusclub.user.model.UserMatchers.userEntityToUserDto;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto findUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id %s not found".formatted(id));
        }
        return userEntityToUserDto(user.get());
    }
}
