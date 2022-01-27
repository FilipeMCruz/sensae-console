package sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table("data")
public class ProcessedSensorDataDAOImpl {

    @Id
    @Column("data_id")
    public String dataId;

    @Column("device_id")
    public String deviceId;

    @Column("device_name")
    public String deviceName;

    @Column("reported_at")
    public Timestamp reportedAt;

    @Column("gps_data")
    public String gpsData;

    @Column("motion")
    public Byte motion;

    @Column("ts")
    public Timestamp ts;
}
