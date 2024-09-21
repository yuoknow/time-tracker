package yu.know.timetracker.user.command.handler.create;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import yu.know.timetracker.core.Result;
import yu.know.timetracker.user.domain.User;
import yu.know.timetracker.user.out.persistence.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserHandler {
    private final UserRepository repository;

    public Result<User> create(User user) {
        try {
            return Result.ok(repository.save(user));
        } catch (Exception e) {
            log.error("Error on saving user {}", user, e);
            throw e;
        }

    }
}
