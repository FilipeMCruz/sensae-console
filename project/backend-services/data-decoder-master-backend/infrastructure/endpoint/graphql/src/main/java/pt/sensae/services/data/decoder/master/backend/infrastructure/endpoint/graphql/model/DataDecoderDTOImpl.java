package pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.data.decoder.master.backend.application.DataDecoderDTO;

public class DataDecoderDTOImpl implements DataDecoderDTO {

    public SensorTypeIdDTOImpl data;
    public String script;

    public String lastTimeSeen;
}
