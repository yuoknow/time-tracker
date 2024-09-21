package yu.know.timetracker.tracker.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yu.know.timetracker.core.ValidationException;
import yu.know.timetracker.tracker.command.handler.StopTimeTrackingHandler;
import yu.know.timetracker.tracker.domain.UserActivity;

import static yu.know.timetracker.tracker.command.handler.StopTimeTrackingHandler.*;

@RestController
@RequiredArgsConstructor
public class StopTimeTrackingController {
    private final StopTimeTrackingHandler handler;

    @PostMapping("/api/time-tracking:stop")
    public ResponseEntity<UserActivity> startTracking(@RequestBody UnvalidatedStopTimeTrackingRequest request) {
        return handler.handle(request)
                .mapOrThrow(r -> ResponseEntity.ok().build(), ValidationException::new);
    }
}
