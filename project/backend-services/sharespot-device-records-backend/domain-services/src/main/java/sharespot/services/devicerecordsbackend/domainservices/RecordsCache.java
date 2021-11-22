package sharespot.services.devicerecordsbackend.domainservices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domain.model.records.*;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecordsCache {

    private final Map<DeviceId, Map.Entry<Records, LastTimeQueried>> cache;
    private final TreeMap<LastTimeQueried, DeviceId> reverseCache;
    private final RecordsRepository repository;
    @Value("${cache.maxSize}")
    private Integer cacheMaxSize;

    public RecordsCache(RecordsRepository repository) {
        this.repository = repository;
        this.cache = new HashMap<>();
        this.reverseCache = new TreeMap<>();
    }

    public DeviceId removeRecord(DeviceId id) {
        var removed = cache.remove(id);
        reverseCache.remove(removed.getValue());
        return repository.delete(id);
    }

    public void indexRecord(DeviceRecords records) {
        toCache(records.getDeviceId(), updateRecordTime(records.getRecords()));
        repository.save(records);
    }

    public Records seekRecordsFor(DeviceId id) {
        var records = cache.get(id);
        if (records == null) {
            return seekInRepo(id);
        } else {
            toCache(id, updateRecordTime(records.getKey()));
            return records.getKey();
        }
    }

    private Records seekInRepo(DeviceId id) {
        var record = repository.findByDeviceId(id);
        if (record.isPresent()) {
            toCache(record.get().getDeviceId(), updateRecordTime(record.get().getRecords()));
            return record.get().getRecords();
        } else {
            var empty = Records.empty();
            toCache(id, updateRecordTime(empty));
            return empty;
        }
    }

    private void toCache(DeviceId id, Map.Entry<Records, LastTimeQueried> record) {
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

    private Map.Entry<Records, LastTimeQueried> updateRecordTime(Records records) {
        return new AbstractMap.SimpleEntry<>(records, LastTimeQueried.now());
    }
}
