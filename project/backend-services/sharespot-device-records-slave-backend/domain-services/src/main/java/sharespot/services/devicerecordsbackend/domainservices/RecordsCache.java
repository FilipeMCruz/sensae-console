package sharespot.services.devicerecordsbackend.domainservices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.domain.model.records.LastTimeQueried;
import sharespot.services.devicerecordsbackend.domain.model.records.RecordsRepository;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecordsCache {

    private final Map<DeviceId, Map.Entry<DeviceRecords, LastTimeQueried>> cache;
    private final TreeMap<LastTimeQueried, DeviceId> reverseCache;
    private final RecordsRepository repository;
    @Value("${cache.maxSize}")
    private Integer cacheMaxSize;

    public RecordsCache(RecordsRepository repository) {
        this.repository = repository;
        this.cache = new HashMap<>();
        this.reverseCache = new TreeMap<>();
    }

    public void removeRecord(DeviceId id) {
        var removed = cache.remove(id);
        reverseCache.remove(removed.getValue());
    }

    public void indexRecord(DeviceRecords records) {
        toCache(records.device().id(), updateRecordTime(records));
    }

    public DeviceRecords seekRecordsFor(DeviceId id) {
        var records = cache.get(id);
        if (records == null) {
            return seekInRepo(id);
        } else {
            toCache(id, updateRecordTime(records.getKey()));
            return records.getKey();
        }
    }

    private DeviceRecords seekInRepo(DeviceId id) {
        var record = repository.findByDeviceId(id);
        if (record.isPresent()) {
            toCache(record.get().device().id(), updateRecordTime(record.get()));
            return record.get();
        } else {
            var empty = DeviceRecords.empty(id);
            toCache(id, updateRecordTime(empty));
            return empty;
        }
    }

    private void toCache(DeviceId id, Map.Entry<DeviceRecords, LastTimeQueried> record) {
        if (cache.size() >= cacheMaxSize) {
            removeEntry();
        }
        cache.put(id, record);
        reverseCache.put(record.getValue(), id);
    }

    private void removeEntry() {
        var lastEntry = reverseCache.pollLastEntry();
        cache.remove(lastEntry.getValue());
    }

    private Map.Entry<DeviceRecords, LastTimeQueried> updateRecordTime(DeviceRecords records) {
        return new AbstractMap.SimpleEntry<>(records, LastTimeQueried.now());
    }
}
