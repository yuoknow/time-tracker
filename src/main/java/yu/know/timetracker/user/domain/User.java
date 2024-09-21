package yu.know.timetracker.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import yu.know.timetracker.core.Result;

import java.util.UUID;

@Getter
@ToString
@Table("users")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class User {
    @Id
    private final UUID id;
    @Version
    private final int version;
    @Embedded.Nullable
    private final UserName name;


    public static Result<User> from(String idStr, String nameStr) {
        var userId = UserId.of(idStr);
        var userName = UserName.of(nameStr);

        return userId.merge(userName, (id, name) -> new User(id.getValue(), 0, name));
    }

}
