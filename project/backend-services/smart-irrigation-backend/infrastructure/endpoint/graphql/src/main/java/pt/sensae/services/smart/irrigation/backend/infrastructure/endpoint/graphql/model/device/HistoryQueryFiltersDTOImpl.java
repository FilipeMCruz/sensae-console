package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device;

import java.util.List;

public class HistoryQueryFiltersDTOImpl {

    public List<String> devices;

    public List<String> irrigationZones;

    public String startTime;

    public String endTime;
}
