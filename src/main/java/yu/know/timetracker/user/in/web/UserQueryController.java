package yu.know.timetracker.user.in.web;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import yu.know.timetracker.core.Result;
import yu.know.timetracker.core.ValidationException;
import yu.know.timetracker.user.domain.User;
import yu.know.timetracker.user.domain.UserId;
import yu.know.timetracker.user.out.persistence.UserRepository;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserQueryController {
    private final UserRepository userRepository;

    @GetMapping("/api/users")
    public Set<UserView> getAllUsers() {
        var users = new HashSet<UserView>();
        for (User user : userRepository.findAll()) {
            users.add(new UserView(user.getId().toString(), user.getName().toString()));
        }
        return users;
    }

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<UserView> getUser(@PathVariable String userId) {
        return UserId.of(userId)
                .flatMap(validatedId -> Result.fromOptional(userRepository.findById(validatedId.getValue()),
                                "User not found by id"))
                .mapResult(user -> new UserView(user.getId().toString(), user.getName().toString()))
                .mapOrThrow(ResponseEntity::ok, ValidationException::new);
    }


    public record UserView(@NonNull String id, @NonNull String name) {
    }
}
