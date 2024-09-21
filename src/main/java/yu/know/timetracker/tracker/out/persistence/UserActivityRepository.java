package yu.know.timetracker.tracker.out.persistence;

import org.springframework.data.repository.CrudRepository;
import yu.know.timetracker.tracker.domain.UserActivity;
import yu.know.timetracker.tracker.domain.UserId;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface UserActivityRepository extends CrudRepository<UserActivity, UUID> {
    List<UserActivity> findByUserIdAndStartTimeBetween(UserId userId, OffsetDateTime from, OffsetDateTime to);
}
