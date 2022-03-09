package sharespot.services.fleetmanagementbackend.infrastructure.persistence.questdb.model;

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
    public String motion;

    @Column("ts")
    public Timestamp ts;

    @Column("domain")
    public String domainId;

    public ProcessedSensorDataDAOImpl cloneWithDomain(String domain) {
        var dao = new ProcessedSensorDataDAOImpl();
        dao.dataId = dataId;
        dao.deviceId = deviceId;
        dao.deviceName = deviceName;
        dao.reportedAt = reportedAt;
        dao.motion = motion;
        dao.gpsData = gpsData;
        dao.domainId = domain;
        return dao;
    }
}
