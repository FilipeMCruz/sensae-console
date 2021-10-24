package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.hotspot.HotSpotDTO;

import java.util.List;
import java.util.UUID;

public abstract class SensorData {

    @JsonProperty("uuid")
    public UUID uuid;

    @JsonProperty("id")
    public UUID deviceId;

    @JsonProperty("app_eui")
    public String appEui;

    @JsonProperty("dev_eui")
    public String devEui;

    @JsonProperty("devaddr")
    public String devAddr;

    @JsonProperty("downlink_url")
    public String downLinkURL;

    @JsonProperty("fcnt")
    public Integer fnct;

    @JsonProperty("name")
    public String name;

    @JsonProperty("payload")
    public String payload;

    @JsonProperty("payload_size")
    public Integer payloadSize;

    @JsonProperty("port")
    public Integer port;

    @JsonProperty("reported_at")
    public Long reportDate;

    @JsonProperty("metadata")
    public SensorMetadataDTO metadata;

    @JsonProperty("hotspots")
    public List<HotSpotDTO> hotspots;
}
