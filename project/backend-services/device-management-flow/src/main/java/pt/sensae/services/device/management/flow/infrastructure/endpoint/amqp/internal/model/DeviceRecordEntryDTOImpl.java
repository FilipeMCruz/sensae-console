package pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class DeviceRecordEntryDTOImpl {

    public String label;

    public String content;
}
