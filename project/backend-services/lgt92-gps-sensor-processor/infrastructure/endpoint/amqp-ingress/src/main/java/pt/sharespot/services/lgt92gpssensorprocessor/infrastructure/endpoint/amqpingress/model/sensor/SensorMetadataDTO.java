package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.label.LabelDTO;

import java.util.List;
import java.util.UUID;

public class SensorMetadataDTO {

    @JsonProperty("adr_allowed")
    public Boolean adrAllowed;

    @JsonProperty("multi_buy")
    public Integer multiBuy;

    @JsonProperty("organization_id")
    public UUID organizationId;

    @JsonProperty("labels")
    public List<LabelDTO> labels;
}
