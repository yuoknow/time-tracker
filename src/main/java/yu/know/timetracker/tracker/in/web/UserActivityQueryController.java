package yu.know.timetracker.tracker.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yu.know.timetracker.core.ValidationException;
import yu.know.timetracker.tracker.query.handler.UserActivityQueryHandler;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static yu.know.timetracker.tracker.query.handler.UserActivityQueryHandler.TaskActivity;
import static yu.know.timetracker.tracker.query.handler.UserActivityQueryHandler.UnvalidatedUserActivityByTaskRequest;

@RestController
@RequiredArgsConstructor
public class UserActivityQueryController {
    private final UserActivityQueryHandler handler;

    @GetMapping("/api/time-tracking/activities:byTask")
    public ResponseEntity<Set<TaskActivity>> getUserActivitiesByTask(@RequestParam String userId,
                                                                     @RequestParam LocalDate from,
                                                                     @RequestParam LocalDate to) {
        return handler.getActivitiesByTask(new UnvalidatedUserActivityByTaskRequest(userId, from, to))
                .mapOrThrow(ResponseEntity::ok, ValidationException::new);
    }

    @GetMapping("/api/time-tracking/activities:byPeriod")
    public List<TaskActivity> getUserActivitiesByPeriod(@RequestParam String userId,
                                                        @RequestParam LocalDate from,
                                                        @RequestParam LocalDate to) {
        return Collections.emptyList();
    }

    @GetMapping("/api/time-tracking/activities:sumTime")
    public List<TaskActivity> getUserActivitiesSum(@RequestParam String userId,
                                                   @RequestParam LocalDate from,
                                                   @RequestParam LocalDate to) {
        return Collections.emptyList();
    }
}
