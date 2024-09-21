package yu.know.timetracker.user.out.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import yu.know.timetracker.user.domain.User;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    @Query("UPDATE users SET name = :name, version = version + 1 WHERE id = :id RETURNING *")
    User updateName(UUID id, String name);
}
