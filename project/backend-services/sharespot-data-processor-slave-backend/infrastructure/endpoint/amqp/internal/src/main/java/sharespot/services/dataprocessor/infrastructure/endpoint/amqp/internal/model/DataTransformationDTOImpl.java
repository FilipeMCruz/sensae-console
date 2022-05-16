package sharespot.services.dataprocessor.infrastructure.endpoint.amqp.internal.model;


import sharespot.services.dataprocessor.application.DataTransformationDTO;

import java.util.Set;

public class DataTransformationDTOImpl implements DataTransformationDTO {
    
    public Set<PropertyTransformationDTOImpl> entries;
    
}
