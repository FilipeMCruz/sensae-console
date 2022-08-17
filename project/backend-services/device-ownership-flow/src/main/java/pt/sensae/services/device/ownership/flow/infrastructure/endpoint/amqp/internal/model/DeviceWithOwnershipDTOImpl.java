package pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

@RegisterForReflection
public class DeviceWithOwnershipDTOImpl {

    public String deviceId;

    public List<String> owners;

}
