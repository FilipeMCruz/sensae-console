package pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class DeviceSubSensorDTOImpl {

    public DeviceInformationDTOImpl controller;

    public Integer subDeviceRef;

    public String subDeviceId;
}
