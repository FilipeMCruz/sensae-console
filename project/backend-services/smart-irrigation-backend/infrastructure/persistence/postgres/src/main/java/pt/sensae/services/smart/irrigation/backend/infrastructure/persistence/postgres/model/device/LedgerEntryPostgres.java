package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.device;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table("ledger_entries")
public class LedgerEntryPostgres {

    @Id
    public Long persistenceId;

    @Column("device_id")
    public String deviceId;

    @Column("device_name")
    public String deviceName;

    @Column("open_at")
    public Timestamp openAt;

    @Column("close_at")
    public Timestamp closeAt;

    @Column("latitude")
    public String latitude;

    @Column("longitude")
    public String longitude;

    @Column("altitude")
    public String altitude;

    @Column("ownership")
    public String ownership;

    public LedgerEntryPostgres() {
    }

    public LedgerEntryPostgres(String deviceId, Timestamp openAt, Timestamp closeAt, String deviceName, String latitude, String longitude, String altitude, String ownership) {
        this.deviceId = deviceId;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.deviceName = deviceName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.ownership = ownership;
    }
}
