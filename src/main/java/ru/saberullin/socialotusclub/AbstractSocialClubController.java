package ru.saberullin.socialotusclub;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.saberullin.socialotusclub.security.AuthenticationException;
import ru.saberullin.socialotusclub.user.UserNotFoundException;

import java.util.Map;

@RestController
public class AbstractSocialClubController {

    public ResponseEntity<?> createResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(message);
    }

    public ResponseEntity<?> createResponse(HttpStatus status, Object responseEntity) {
        return ResponseEntity.status(status).body(responseEntity);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNullPointerException(final NullPointerException e) {
        return Map.of(
                "error", "Possibly no parameter set.",
                "message", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleAuthenticationException(final AuthenticationException e) {
        return Map.of(
                "error", "Authentication error",
                "message", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(final UserNotFoundException e) {
        return Map.of(
                "error", "User not found",
                "message", e.getMessage()
        );
    }
}
