package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device;

import java.util.List;

public class HistoryQueryFiltersDTOImpl {

    public List<String> devices;

    public List<String> gardens;

    public String startTime;

    public String endTime;
}
