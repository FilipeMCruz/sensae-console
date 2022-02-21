package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.repository.util;

import java.util.List;
import java.util.stream.Collectors;

public class PostgresArrayMapper {

    public static String toArray(String value) {
        return "{" + value + "}";
    }

    public static String toArray(List<String> value) {
        return value.stream().collect(Collectors.joining(",", "{", "}"));
    }
}
