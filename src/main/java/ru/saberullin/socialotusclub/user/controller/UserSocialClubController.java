package ru.saberullin.socialotusclub.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saberullin.socialotusclub.AbstractSocialClubController;
import ru.saberullin.socialotusclub.user.service.UserService;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/user")
public class UserSocialClubController extends AbstractSocialClubController {

    private final UserService userService;

    public UserSocialClubController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return createResponse(OK, userService.findUserById(id));
    }
}
