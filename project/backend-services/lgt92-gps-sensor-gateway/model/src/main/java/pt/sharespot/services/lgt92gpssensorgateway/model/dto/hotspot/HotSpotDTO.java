package pt.sharespot.services.lgt92gpssensorgateway.model.dto.hotspot;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HotSpotDTO {

    @JsonProperty("channel")
    public Integer channel;

    @JsonProperty("frequency")
    public Double frequency;

    @JsonProperty("id")
    public String id;

    @JsonProperty("lat")
    public Double latitude;

    @JsonProperty("long")
    public Double longitude;

    @JsonProperty("name")
    public String name;

    @JsonProperty("reported_at")
    public Long reportDate;

    @JsonProperty("rssi")
    public Double rssi;

    @JsonProperty("snr")
    public Double snr;

    @JsonProperty("spreading")
    public String spreading;

    @JsonProperty("status")
    public String status;
}
