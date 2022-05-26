package pt.sensae.services.device.commander.backend.domain.model.commands.request;

public class CommandRequestDTO {
    public String payload_raw;
    public Integer port;
    public Boolean confirmed;

    @Override
    public String toString() {
        return "{" +
                "payload_raw='" + payload_raw + '\'' +
                ", port=" + port +
                ", confirmed=" + confirmed +
                '}';
    }
}
