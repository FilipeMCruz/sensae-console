package pt.sensae.services.device.management.master.backend.domain.model.records;

import pt.sensae.services.device.management.master.backend.domain.model.exceptions.NotValidException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record DeviceRecords(Set<BasicRecordEntry> entries) {
    public DeviceRecords {
        if (entries.stream().map(BasicRecordEntry::label).collect(Collectors.toSet()).size() != entries.size()) {
            throw new NotValidException("Record Entries must have unique labels");
        }
    }

    public static DeviceRecords empty() {
        return new DeviceRecords(new HashSet<>());
    }
}
