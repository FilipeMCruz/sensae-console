package sharespot.services.devicerecordsbackend.domain.model.sensors;

import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.domain.model.records.Records;

public class ProcessedSensorDataWithRecord extends ProcessedSensorData {

    private final Records records;

    public ProcessedSensorDataWithRecord(ProcessedSensorData sensorData, DeviceRecords records) {
        super(sensorData.getDataId(), sensorData.getDevice().withDevice(records.device()), sensorData.getReportedAt(), sensorData.getData());
        this.records = records.records();
    }

    public Records getRecords() {
        return records;
    }
}
