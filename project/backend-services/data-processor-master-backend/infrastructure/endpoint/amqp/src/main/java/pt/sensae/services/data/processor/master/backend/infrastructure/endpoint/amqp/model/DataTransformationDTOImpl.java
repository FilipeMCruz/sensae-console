package pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.amqp.model;

import pt.sensae.services.data.processor.master.backend.application.DataTransformationDTO;

import java.util.Set;

public class DataTransformationDTOImpl implements DataTransformationDTO {
    
    public Set<PropertyTransformationDTOImpl> entries;
    
}
