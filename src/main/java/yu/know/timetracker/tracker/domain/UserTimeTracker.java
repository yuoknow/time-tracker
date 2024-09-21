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

import static yu.know.timetracker.tracker.command.handler.StartTimeTrackingHandler.UnvalidatedStartTimeTrackingRequest;

@Getter
@ToString
@Table("users_time_tracker")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTimeTracker {
    @Id
    private final UUID userId;
    @Version
    private final int version;
    @Embedded.Nullable
    private final TaskId taskId;
    private final OffsetDateTime startedAt;

    public static @NonNull Result<UserTimeTracker> create(UnvalidatedStartTimeTrackingRequest request) {
        var taskIdRes = TaskId.of(request.taskId());
        var userIdRes = UserId.of(request.userId());

        return userIdRes.merge(taskIdRes, (userId, taskId) -> new UserTimeTracker(
                userId.getValue(),
                0,
                taskId,
                OffsetDateTime.now()
        ));
    }

}
