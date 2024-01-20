package ru.saberullin.socialotusclub.user.model;

public class UserMatchers {

    private UserMatchers() {}

    public static UserDto userEntityToUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .age(userEntity.getAge())
                .sex(userEntity.getSex())
                .interests(userEntity.getInterests())
                .city(userEntity.getCity())
                .build();
    }
}
