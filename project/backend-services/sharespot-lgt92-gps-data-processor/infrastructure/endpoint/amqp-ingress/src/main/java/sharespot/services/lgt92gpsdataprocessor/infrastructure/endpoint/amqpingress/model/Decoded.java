
package sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpingress.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "payload",
    "status"
})
public class Decoded {

    @JsonProperty("payload")
    public Payload payload;
    @JsonProperty("status")
    public String status;

}
