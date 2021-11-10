
package pt.sharespot.services.lgt92gpssensorgateway.model.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "app_eui",
    "decoded",
    "dev_eui",
    "devaddr",
    "downlink_url",
    "fcnt",
    "hotspots",
    "id",
    "metadata",
    "name",
    "payload",
    "payload_size",
    "port",
    "reported_at",
    "uuid"
})
public class SensorData {

    @JsonProperty("app_eui")
    public String appEui;
    @JsonProperty("decoded")
    public Decoded decoded;
    @JsonProperty("dev_eui")
    public String devEui;
    @JsonProperty("devaddr")
    public String devaddr;
    @JsonProperty("downlink_url")
    public String downlinkUrl;
    @JsonProperty("fcnt")
    public Integer fcnt;
    @JsonProperty("hotspots")
    public List<Hotspot> hotspots = null;
    @JsonProperty("id")
    public String id;
    @JsonProperty("metadata")
    public Metadata metadata;
    @JsonProperty("name")
    public String name;
    @JsonProperty("payload")
    public String payload;
    @JsonProperty("payload_size")
    public Integer payloadSize;
    @JsonProperty("port")
    public Integer port;
    @JsonProperty("reported_at")
    public Long reportedAt;
    @JsonProperty("uuid")
    public String uuid;

}
