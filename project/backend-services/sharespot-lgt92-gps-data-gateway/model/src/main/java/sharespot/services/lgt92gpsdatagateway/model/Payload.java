
package sharespot.services.lgt92gpsdatagateway.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ALARM_status",
    "Accuracy",
    "Altitude",
    "BatV",
    "FW",
    "LON",
    "Latitude",
    "Longitude",
    "MD",
    "Pitch",
    "Roll"
})
public class Payload {

    @JsonProperty("ALARM_status")
    public Boolean aLARMStatus;
    @JsonProperty("Accuracy")
    public Integer accuracy;
    @JsonProperty("Altitude")
    public Integer altitude;
    @JsonProperty("BatV")
    public Double batV;
    @JsonProperty("FW")
    public Integer fw;
    @JsonProperty("LON")
    public String lon;
    @JsonProperty("Latitude")
    public Double latitude;
    @JsonProperty("Longitude")
    public Double longitude;
    @JsonProperty("MD")
    public String md;
    @JsonProperty("Pitch")
    public Integer pitch;
    @JsonProperty("Roll")
    public Integer roll;

}
