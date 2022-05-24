package pt.sensae.services.rule.management.infrastructure.persistence.postgres.repository.util;

import java.util.List;
import java.util.stream.Collectors;

public class PostgresArrayMapper {

    public static String toArray(List<String> value) {
        if (value.size() == 1) {
            return "{" + value.get(0) + "}";
        }
        return value.stream().collect(Collectors.joining(",", "{", "}"));
    }
}
