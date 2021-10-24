package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.label;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class LabelDTO {
    @JsonProperty("name")
    public String name;

    @JsonProperty("id")
    public UUID id;

    @JsonProperty("organization_id")
    public UUID organizationId;
}
