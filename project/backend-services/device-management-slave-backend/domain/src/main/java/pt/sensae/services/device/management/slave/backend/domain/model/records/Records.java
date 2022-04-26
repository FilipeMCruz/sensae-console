package pt.sensae.services.device.management.slave.backend.domain.model.records;

import java.util.ArrayList;
import java.util.List;

public record Records(List<RecordEntry> entries) {
    public static Records empty() {
        return new Records(new ArrayList<>());
    }
}
