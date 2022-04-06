package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table("smart_irrigation_data")
public class DataQuestDB {

    @Id
    @Column("data_id")
    public String dataId;

    @Column("device_id")
    public String deviceId;

    @Column("device_type")
    public String deviceType;

    @Column("reported_at")
    public Timestamp reportedAt;

    @Column("payload_temperature")
    public Float temperature;

    @Column("payload_humidity")
    public Float humidity;

    @Column("payload_soil_moisture")
    public Float soilMoisture;

    @Column("payload_illuminance")
    public Float illuminance;
}
