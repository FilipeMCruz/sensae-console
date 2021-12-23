package sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.model;

import sharespot.services.dataprocessormaster.application.DataTransformationDTO;

import java.util.Set;

public class DataTransformationDTOImpl implements DataTransformationDTO {
    public SensorTypeIdDTOImpl type;
    public Set<PropertyTransformationDTOImpl> entries;
}
