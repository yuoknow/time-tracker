package yu.know.timetracker.tracker.query.handler;

import lombok.experimental.UtilityClass;
import yu.know.timetracker.tracker.domain.UserActivity;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static yu.know.timetracker.tracker.query.handler.UserActivityQueryHandler.TaskActivity;

@UtilityClass
public class UserActivityMapper {

    public static Set<TaskActivity> toTaskActivities(List<UserActivity> userActivities) {
        var minutesByTask = userActivities.stream().collect(Collectors.toMap(UserActivity::getTaskId,
                a -> Duration.between(a.getStartTime(), a.getEndTime()).toMinutes(),
                Long::sum));

        return minutesByTask.entrySet().stream().map(e ->
                        new TaskActivity(e.getKey().getValue(),
                                String.format("%02d:%02d",
                                Duration.ofMinutes(e.getValue()).toHours(),
                                Duration.ofMinutes(e.getValue()).toMinutesPart())))
                .collect(Collectors.toSet());
    }
}
