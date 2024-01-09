package ru.saberullin.socialotusclub.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saberullin.socialotusclub.role.model.Role;
import ru.saberullin.socialotusclub.role.repository.RoleRepository;
import ru.saberullin.socialotusclub.user.model.UserEntity;
import ru.saberullin.socialotusclub.user.model.UserLoginDto;
import ru.saberullin.socialotusclub.user.model.UserRegisterDto;
import ru.saberullin.socialotusclub.user.repository.UserRepository;

import java.util.Optional;

@Service
@Slf4j
@Transactional
public class AuthenticationService {
    private final static String USER_ROLE = "user";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void register(UserRegisterDto registerDto) {
        String username = registerDto.getName();

        if (userRepository.existsByName(username)) {
            throw new AuthenticationException("Username %s already exists".formatted(username));
        }
        UserEntity user = UserEntity.builder()
                .name(username)
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();

        Optional<Role> role = roleRepository.findByRole(USER_ROLE);
        if (role.isEmpty()) {
            String errorMsg = "Role %s does not exist".formatted(USER_ROLE);
            log.error(errorMsg + " Check roles in database.");
            throw new AuthenticationException("Role %s does not exist".formatted(USER_ROLE));
        }

        UserEntity savedUser = userRepository.saveUser(user);
        userRepository.saveUserRole(savedUser, role.get());
    }

    public void login(UserLoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
