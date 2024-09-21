package yu.know.timetracker.tracker.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jspecify.annotations.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import yu.know.timetracker.core.Result;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@ToString
@Table("users_activity")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserActivity {
    @Id
    private final UUID id;
    @Version
    private final int version;
    @Embedded.Nullable
    private final TaskId taskId;
    @Embedded.Nullable
    private final UserId userId;
    private final OffsetDateTime startTime;
    private final OffsetDateTime endTime;

    public static @NonNull Result<UserActivity> from(UUID id, UserTimeTracker userTimeTracker, OffsetDateTime endTime) {
        return UserId.of(userTimeTracker.getUserId())
                .mapResult(userId -> new UserActivity(
                        id,
                        0,
                        userTimeTracker.getTaskId(),
                        userId,
                        userTimeTracker.getStartedAt(),
                        endTime
                ));
    }
}
