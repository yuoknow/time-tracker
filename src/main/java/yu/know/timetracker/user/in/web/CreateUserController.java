package yu.know.timetracker.user.in.web;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yu.know.timetracker.core.ValidationException;
import yu.know.timetracker.user.domain.User;
import yu.know.timetracker.user.command.handler.create.CreateUserHandler;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class CreateUserController {
    private final CreateUserHandler handler;

    @PostMapping("/api/users")
    public ResponseEntity<UserCreated> createUser(@RequestBody UnvalidatedCreateUserRequest createUserRequest) {
        return User.from(createUserRequest.id, createUserRequest.name)
                .flatMap(handler::create)
                .mapResult(u -> new UserCreated(u.getId(), u.getName().getValue()))
                .mapOrThrow(ResponseEntity::ok, ValidationException::new);
    }

    public record UnvalidatedCreateUserRequest(String id, String name) {
    }

    public record UserCreated(@NonNull UUID id, @NonNull String name) {
    }
}
