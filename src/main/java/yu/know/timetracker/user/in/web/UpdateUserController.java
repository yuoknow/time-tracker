package yu.know.timetracker.user.in.web;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yu.know.timetracker.core.ValidationException;
import yu.know.timetracker.user.domain.UserId;
import yu.know.timetracker.user.domain.UserName;
import yu.know.timetracker.user.command.handler.update.UpdateUserHandler;

import java.util.UUID;

import static yu.know.timetracker.user.command.handler.update.UpdateUserHandler.*;


@RestController
@RequiredArgsConstructor
public class UpdateUserController {
    private final UpdateUserHandler handler;

    @PutMapping("/api/users")
    public ResponseEntity<UserUpdated> updateUser(@RequestBody UnvalidatedUpdateUserRequest updateUserRequest) {
        return UserId.of(updateUserRequest.id)
                .merge(UserName.of(updateUserRequest.name), ValidatedUpdateRequest::new)
                .flatMap(handler::update)
                .mapResult(u -> new UserUpdated(u.getId(), u.getName().getValue()))
                .mapOrThrow(ResponseEntity::ok, ValidationException::new);
    }

    public record UnvalidatedUpdateUserRequest(String id, String name) {
    }

    public record UserUpdated(@NonNull UUID id, @NonNull String name) {
    }

}
