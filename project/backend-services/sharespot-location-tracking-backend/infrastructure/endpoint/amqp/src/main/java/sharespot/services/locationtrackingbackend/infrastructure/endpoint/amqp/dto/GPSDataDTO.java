package sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.dto;

import java.util.UUID;

public final class GPSDataDTO {
    public UUID dataId;
    public UUID deviceId;
    public Long reportedAt;
    public GPSDataDetailsDTO data;
}
