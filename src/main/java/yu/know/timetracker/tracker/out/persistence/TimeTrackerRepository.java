package yu.know.timetracker.tracker.out.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import yu.know.timetracker.tracker.domain.UserTimeTracker;

import java.util.UUID;

public interface TimeTrackerRepository extends CrudRepository<UserTimeTracker, UUID> {
    @Query("DELETE FROM users_time_tracker WHERE task_id = :taskId AND user_id = :userId RETURNING *")
    UserTimeTracker deleteByTaskIdAndUserId(String taskId, UUID userId);
}
