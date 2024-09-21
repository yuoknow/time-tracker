package yu.know.timetracker.user.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jspecify.annotations.NonNull;
import org.springframework.data.relational.core.mapping.Column;
import yu.know.timetracker.core.Result;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserName {
    @Column("name")
    private final String value;

    public static @NonNull Result<UserName> of(String name) {
        if (name == null) return Result.failure("name is null");
        if (name.isEmpty()) return Result.failure("name is empty");
        if (name.length() > 127) return Result.failure("name is too long");

        return Result.ok(new UserName(name));
    }
}
