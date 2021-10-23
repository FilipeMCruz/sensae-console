package pt.sharespot.services.lgt92gpssensorgateway.model.dto.sensor.lgt92sensor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LGT92PayloadDataDTO {

    @JsonProperty("ALARM_status")
    public Boolean alarmStatus;

    @JsonProperty("Accuracy")
    public Integer accuracy;

    @JsonProperty("Altitude")
    public Integer altitude;

    @JsonProperty("BatV")
    public Double batVolts;

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
