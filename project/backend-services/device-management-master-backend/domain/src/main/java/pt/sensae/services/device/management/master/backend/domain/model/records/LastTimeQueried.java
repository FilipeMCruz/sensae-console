package pt.sensae.services.device.management.master.backend.domain.model.records;

import java.time.LocalDateTime;

public record LastTimeQueried(LocalDateTime time) implements Comparable<LastTimeQueried> {
    public static LastTimeQueried now() {
        return new LastTimeQueried(LocalDateTime.now());
    }

    @Override
    public int compareTo(LastTimeQueried lastTimeQueried) {
        return this.time.compareTo(lastTimeQueried.time);
    }
}
