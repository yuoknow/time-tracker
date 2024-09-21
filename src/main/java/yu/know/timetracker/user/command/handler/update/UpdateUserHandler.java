package yu.know.timetracker.user.command.handler.update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import yu.know.timetracker.core.Result;
import yu.know.timetracker.user.domain.User;
import yu.know.timetracker.user.domain.UserId;
import yu.know.timetracker.user.domain.UserName;
import yu.know.timetracker.user.out.persistence.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateUserHandler {
    private final UserRepository repository;

    public Result<User> update(ValidatedUpdateRequest request) {
        try {
            return Result.ok(repository.updateName(request.id.getValue(), request.userName.getValue()));
        } catch (Exception e) {
            log.error("Error on updating user {}", request, e);
            throw e;
        }
    }

    public record ValidatedUpdateRequest(@NonNull UserId id, @NonNull UserName userName) {
    }
}
