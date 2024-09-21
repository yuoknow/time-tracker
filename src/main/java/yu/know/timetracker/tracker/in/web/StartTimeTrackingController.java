package yu.know.timetracker.tracker.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yu.know.timetracker.core.ValidationException;
import yu.know.timetracker.tracker.command.handler.StartTimeTrackingHandler;

import static yu.know.timetracker.tracker.command.handler.StartTimeTrackingHandler.UnvalidatedStartTimeTrackingRequest;

@RestController
@RequiredArgsConstructor
public class StartTimeTrackingController {
    private final StartTimeTrackingHandler handler;

    @PostMapping("/api/time-tracking:start")
    public ResponseEntity<Void> startTracking(@RequestBody UnvalidatedStartTimeTrackingRequest request) {
        return handler.handle(request)
                .mapOrThrow(r -> ResponseEntity.ok().build(), ValidationException::new);
    }
}
