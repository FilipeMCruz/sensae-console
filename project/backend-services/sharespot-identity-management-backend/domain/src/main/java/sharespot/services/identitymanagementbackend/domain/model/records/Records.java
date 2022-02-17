package sharespot.services.identitymanagementbackend.domain.model.records;

import sharespot.services.identitymanagementbackend.domain.model.exceptions.NotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record Records(List<RecordEntry> entries) {
    public Records {
        if (entries.stream().map(RecordEntry::getLabel).collect(Collectors.toSet()).size() != entries.size()) {
            throw new NotValidException("Record Entries must have unique labels");
        }
    }

    public static Records empty() {
        return new Records(new ArrayList<>());
    }
}
