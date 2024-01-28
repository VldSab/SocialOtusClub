package ru.saberullin.socialotusclub.user.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.saberullin.socialotusclub.security.AuthenticationService;
import ru.saberullin.socialotusclub.user.UserNotFoundException;
import ru.saberullin.socialotusclub.user.model.UserDto;
import ru.saberullin.socialotusclub.user.model.UserEntity;
import ru.saberullin.socialotusclub.user.model.UserMatchers;
import ru.saberullin.socialotusclub.user.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.saberullin.socialotusclub.user.model.UserMatchers.userEntityToUserDto;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final SyntheticNamesLoader syntheticNamesLoader;

    public UserServiceImpl(UserRepository userRepository, AuthenticationService authenticationService, SyntheticNamesLoader syntheticNamesLoader) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.syntheticNamesLoader = syntheticNamesLoader;
    }

    @Override
    public UserDto findUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id %s not found".formatted(id));
        }
        return userEntityToUserDto(user.get());
    }

    @Override
    public List<UserDto> findUsersByNameAndSurname(String firstName, String lastName) {
        List<UserEntity> users = userRepository.findByNameAndSurname(firstName, lastName);
        if (users.isEmpty()) {
            throw new UserNotFoundException("User with name started with %s and last name started with %s not found"
                    .formatted(firstName, lastName));
        }
        return users.stream().map(UserMatchers::userEntityToUserDto).toList();
    }

    @Override
    public void fillSynthetic(Integer amount) {
        List<UserEntity> users = new ArrayList<>();
        while (syntheticNamesLoader.hasNext() && users.size() < amount) {
            String[] nameSurname = syntheticNamesLoader.next();
            String username = String.join("", nameSurname) + Instant.now();
            UserEntity savedUser = UserEntity.builder()
                    .username(username)
                    .name(nameSurname[0])
                    .surname(nameSurname[1])
                    .build();
            users.add(savedUser);
        }
        userRepository.saveAllUsers(users);
        syntheticNamesLoader.resetIterator();
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        Optional<UserEntity> user = userRepository.findById(userDto.getId());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with username %s not found".formatted(userDto.getUsername()));
        }

        UserDto updatedUser = UserDto.builder()
                .id(user.get().getId())
                .username(userDto.getUsername() == null ? user.get().getUsername() : userDto.getUsername())
                .name(userDto.getName() == null ? user.get().getName() : userDto.getName())
                .surname(userDto.getSurname() == null ? user.get().getSurname() : userDto.getSurname())
                .age(userDto.getAge() == null ? user.get().getAge() : userDto.getAge())
                .sex(userDto.getSex() == null ? user.get().getSex() : userDto.getSex())
                .city(userDto.getCity() == null ? user.get().getCity() : userDto.getCity())
                .interests(userDto.getInterests() == null ? user.get().getInterests() : userDto.getInterests())
                .build();

        return userEntityToUserDto(userRepository.updateUser(updatedUser));
    }

}
