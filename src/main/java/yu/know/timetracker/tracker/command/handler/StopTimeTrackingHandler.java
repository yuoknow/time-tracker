package yu.know.timetracker.tracker.command.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import yu.know.timetracker.core.Result;
import yu.know.timetracker.tracker.domain.TaskId;
import yu.know.timetracker.tracker.domain.UserTimeTracker;
import yu.know.timetracker.tracker.domain.UserActivity;
import yu.know.timetracker.tracker.domain.UserId;
import yu.know.timetracker.tracker.out.persistence.TimeTrackerRepository;
import yu.know.timetracker.tracker.out.persistence.UserActivityRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class StopTimeTrackingHandler {
    private final TimeTrackerRepository timeTrackerRepository;
    private final UserActivityRepository userActivityRepository;

    @Transactional
    public Result<UserActivity> handle(UnvalidatedStopTimeTrackingRequest request) {
        try {
            return TaskId.of(request.taskId).merge(UserId.of(request.userId),
                            ValidatedStopTimeTrackingRequest::new)
                    .flatMap(this::deleteTimeTracker)
                    .flatMap(this::createActivityPeriod)
                    .flatMap(this::saveActivity);
        } catch (Exception e) {
            log.error("Error on stopping time tracker {}", request, e);
            throw e;
        }
    }

    private Result<UserTimeTracker> deleteTimeTracker(ValidatedStopTimeTrackingRequest request) {
        var timeTracker = timeTrackerRepository.deleteByTaskIdAndUserId(request.taskId.getValue(), request.userId.getValue());
        return Result.ok(timeTracker);
    }

    private Result<UserActivity> createActivityPeriod(UserTimeTracker userTimeTracker) {
        return UserActivity.from(UUID.randomUUID(), userTimeTracker, OffsetDateTime.now());
    }

    private Result<UserActivity> saveActivity(UserActivity trackedPeriod) {
        trackedPeriod = userActivityRepository.save(trackedPeriod);
        return Result.ok(trackedPeriod);
    }

    public record ValidatedStopTimeTrackingRequest(TaskId taskId, UserId userId) {
    }

    public record UnvalidatedStopTimeTrackingRequest(String taskId, String userId) {

    }
}
