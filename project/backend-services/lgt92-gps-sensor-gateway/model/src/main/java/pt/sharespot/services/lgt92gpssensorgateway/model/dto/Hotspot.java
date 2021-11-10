
package pt.sharespot.services.lgt92gpssensorgateway.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "channel",
    "frequency",
    "id",
    "lat",
    "long",
    "name",
    "reported_at",
    "rssi",
    "snr",
    "spreading",
    "status"
})
public class Hotspot {

    @JsonProperty("channel")
    public Integer channel;
    @JsonProperty("frequency")
    public Double frequency;
    @JsonProperty("id")
    public String id;
    @JsonProperty("lat")
    public Double lat;
    @JsonProperty("long")
    public Double _long;
    @JsonProperty("name")
    public String name;
    @JsonProperty("reported_at")
    public Long reportedAt;
    @JsonProperty("rssi")
    public Double rssi;
    @JsonProperty("snr")
    public Double snr;
    @JsonProperty("spreading")
    public String spreading;
    @JsonProperty("status")
    public String status;

}
