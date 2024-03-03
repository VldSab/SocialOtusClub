package ru.saberullin.socialotusclub.user.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saberullin.socialotusclub.AbstractSocialClubController;
import ru.saberullin.socialotusclub.post.Post;
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

    @GetMapping("/search")
    public ResponseEntity<?> getUsers(@RequestParam String firstName, @RequestParam String lastName) {
        return createResponse(OK, userService.findUsersByNameAndSurname(firstName, lastName));
    }

    @GetMapping("/fill-synthetic/{amount}")
    public ResponseEntity<?> fillWithSyntheticUsers(@PathVariable Integer amount) {
        userService.fillSynthetic(amount);
        return createResponse(OK, "Synthetic generated");
    }

    @PostMapping("/friend/add")
    public ResponseEntity<?> addFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        userService.addFriend(userId, friendId);
        return createResponse(OK, "Users successfully made friends");
    }

    @PostMapping("/post/create")
    public ResponseEntity<Post> createPost(@RequestBody @Valid Post post) {
        Post savedPost = userService.createPost(post);
        return (ResponseEntity<Post>) createResponse(OK, savedPost);
    }
}
