package yu.know.timetracker.tracker.command.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import yu.know.timetracker.core.Result;
import yu.know.timetracker.tracker.domain.UserTimeTracker;
import yu.know.timetracker.tracker.out.persistence.TimeTrackerRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartTimeTrackingHandler {
    private final TimeTrackerRepository repository;

    public Result<UserTimeTracker> handle(UnvalidatedStartTimeTrackingRequest request) {
        try {
            return UserTimeTracker.create(request)
                    .flatMap(timeTracker -> Result.ok(repository.save(timeTracker)));
        } catch (Exception e) {
            log.error("Error on starting time tracker {}", request, e);
            throw e;
        }
    }

    public record UnvalidatedStartTimeTrackingRequest(String taskId, String userId) {

    }
}
