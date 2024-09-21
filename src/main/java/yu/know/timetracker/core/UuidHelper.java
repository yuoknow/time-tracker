package yu.know.timetracker.core;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@UtilityClass
public class UuidHelper {

    public static Optional<UUID> fromString(String value) {
        try {
            return Optional.of(UUID.fromString(value));
        } catch (Exception e) {
            log.warn("Invalid UUID: {}", value);
            return Optional.empty();
        }
    }
}
