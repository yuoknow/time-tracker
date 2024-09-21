package yu.know.timetracker.tracker.query.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import yu.know.timetracker.core.Result;
import yu.know.timetracker.tracker.domain.UserActivity;
import yu.know.timetracker.tracker.domain.UserId;
import yu.know.timetracker.tracker.out.persistence.UserActivityRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserActivityQueryHandler {
    private final UserActivityRepository repository;

    public Result<Set<TaskActivity>> getActivitiesByTask(UnvalidatedUserActivityByTaskRequest request) {
        return validate(request)
                .flatMap(this::getUserActivities)
                .mapResult(UserActivityMapper::toTaskActivities);
    }

    private Result<UserActivityByPeriodRequest> validate(UnvalidatedUserActivityByTaskRequest request) {
        return UserId.of(request.userId())
                .mapResult(userId -> new UserActivityByPeriodRequest(userId,
                        request.from.atStartOfDay().atOffset(ZoneOffset.UTC),
                        request.to.atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC)));
    }

    private Result<List<UserActivity>> getUserActivities(UserActivityByPeriodRequest request) {
        return Result.ok(repository
                .findByUserIdAndStartTimeBetween(request.userId, request.from(), request.to()));
    }

    record UserActivityByPeriodRequest(UserId userId, OffsetDateTime from, OffsetDateTime to) {
    }

    public record TaskActivity(String taskId, String trackedTime) {
    }

    public record UnvalidatedUserActivityByTaskRequest(String userId, LocalDate from, LocalDate to) {
    }
}
