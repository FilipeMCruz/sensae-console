
package sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpingress.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "adr_allowed",
    "labels",
    "multi_buy",
    "organization_id"
})
public class Metadata {

    @JsonProperty("adr_allowed")
    public Boolean adrAllowed;
    @JsonProperty("labels")
    public List<Label> labels = null;
    @JsonProperty("multi_buy")
    public Integer multiBuy;
    @JsonProperty("organization_id")
    public String organizationId;

}
