package sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.model;

import sharespot.services.dataprocessormaster.application.DataTransformationDTO;

import java.util.Set;

public class DataTransformationDTOImpl implements DataTransformationDTO {
    public SensorTypeIdDTOImpl data;
    public Set<PropertyTransformationDTOImpl> entries;
}
