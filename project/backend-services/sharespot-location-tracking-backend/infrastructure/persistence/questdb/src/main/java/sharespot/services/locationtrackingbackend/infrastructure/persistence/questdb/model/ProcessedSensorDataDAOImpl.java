package sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table("sensor_gps_data")
public class ProcessedSensorDataDAOImpl {

    @Id
    @Column("data_id")
    public String dataId;

    @Column("device_id")
    public String deviceId;
    
    @Column("device_name")
    public String deviceName;

    @Column("reported_at")
    public Long reportedAt;

    @Column("gps_data")
    public String gpsData;

    @Column("ts")
    public Timestamp ts;
}
