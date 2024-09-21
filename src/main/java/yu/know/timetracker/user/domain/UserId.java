package yu.know.timetracker.user.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import yu.know.timetracker.core.Result;
import yu.know.timetracker.core.UuidHelper;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserId {
    private final UUID value;

    public static Result<UserId> of(String id) {
        if (id == null) return Result.failure("id is null");
        return UuidHelper.fromString(id)
                .map(r -> (Result<UserId>) Result.ok(new UserId(r)))
                .orElseGet(() -> Result.failure("id not uuid"));
    }
}
