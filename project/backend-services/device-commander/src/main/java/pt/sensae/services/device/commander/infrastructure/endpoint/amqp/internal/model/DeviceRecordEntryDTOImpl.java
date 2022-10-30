package pt.sensae.services.device.commander.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class DeviceRecordEntryDTOImpl {

    public String label;

    public String content;
}
