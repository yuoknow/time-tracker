package yu.know.timetracker.tracker.domain;

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
public class TaskId {
    @Column("task_id")
    private final String value;

    public static @NonNull Result<TaskId> of(String id) {
        if (id == null) return Result.failure("task id is null");
        if (id.isEmpty()) return Result.failure("task id is empty");
        if (id.length() > 63) return Result.failure("task id is too long");

        return Result.ok(new TaskId(id));
    }
}
