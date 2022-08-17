package pt.sensae.services.data.processor.flow.infrastructure.endpoint.amqp.internal.model;


import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.Set;

@RegisterForReflection
public class DataTransformationDTOImpl {

    public Set<PropertyTransformationDTOImpl> entries;

}
