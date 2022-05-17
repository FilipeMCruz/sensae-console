package sharespot.services.dataprocessormaster.infrastructure.endpoint.amqp.model;

import sharespot.services.dataprocessormaster.application.DataTransformationDTO;

import java.util.Set;

public class DataTransformationDTOImpl implements DataTransformationDTO {
    
    public Set<PropertyTransformationDTOImpl> entries;
    
}
