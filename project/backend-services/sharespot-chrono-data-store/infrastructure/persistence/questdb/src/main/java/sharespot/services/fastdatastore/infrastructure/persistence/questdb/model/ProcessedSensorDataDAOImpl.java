package sharespot.services.fastdatastore.infrastructure.persistence.questdb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import sharespot.services.fastdatastore.application.ProcessedSensorDataDAO;

import java.sql.Timestamp;

@Table("sensor_gps_data")
public class ProcessedSensorDataDAOImpl implements ProcessedSensorDataDAO {

    @Id
    @Column("data_id")
    public String dataId;

    @Column("device_id")
    public String deviceId;

    @Column("reported_at")
    public Long reportedAt;

    @Column("gps_data")
    public String gpsData;

    @Column("ts")
    public Timestamp ts;
}
