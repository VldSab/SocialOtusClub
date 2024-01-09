package ru.saberullin.socialotusclub.security;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.saberullin.socialotusclub.AbstractController;
import ru.saberullin.socialotusclub.user.model.UserLoginDto;
import ru.saberullin.socialotusclub.user.model.UserRegisterDto;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController extends AbstractController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto registerDto) {
        authenticationService.register(registerDto);
        return createResponse(OK, "User successfully registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto loginDto) {
        authenticationService.login(loginDto);
        return createResponse(OK, "User successfully signed in");
    }
}
