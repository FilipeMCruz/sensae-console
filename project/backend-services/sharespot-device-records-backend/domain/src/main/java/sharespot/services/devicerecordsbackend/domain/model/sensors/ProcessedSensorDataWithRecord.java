package sharespot.services.devicerecordsbackend.domain.model.sensors;

import sharespot.services.devicerecordsbackend.domain.model.records.Records;

public class ProcessedSensorDataWithRecord extends ProcessedSensorData {

    private final Records records;

    public ProcessedSensorDataWithRecord(ProcessedSensorData sensorData, Records records) {
        super(sensorData.getDataId(), sensorData.getDevice(), sensorData.getReportedAt(), sensorData.getData());
        this.records = records;
    }

    public Records getRecords() {
        return records;
    }
}
