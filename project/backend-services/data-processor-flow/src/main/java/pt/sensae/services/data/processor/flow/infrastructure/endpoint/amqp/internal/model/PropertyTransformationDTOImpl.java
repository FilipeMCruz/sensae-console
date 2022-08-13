package pt.sensae.services.data.processor.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PropertyTransformationDTOImpl {
    
    public String oldPath;
    
    public PropertyNameDTOImpl newPath;

    public Integer sensorID;
}
