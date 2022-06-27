package pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.data.processor.master.backend.application.DataTransformationDTO;

import java.util.Set;

public class DataTransformationDTOImpl implements DataTransformationDTO {

    public SensorTypeIdDTOImpl data;

    public Set<PropertyTransformationDTOImpl> entries;

    public String lastTimeSeen;
}
