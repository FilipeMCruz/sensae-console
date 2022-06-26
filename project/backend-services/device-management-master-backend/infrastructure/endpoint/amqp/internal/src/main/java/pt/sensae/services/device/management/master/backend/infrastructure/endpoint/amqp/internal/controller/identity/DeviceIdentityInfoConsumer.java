package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.identity;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.ownership.DeviceIdentityCache;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.mapper.DeviceInformationMapper;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.identity.DeviceIdentityDTOImpl;

@Service
public class DeviceIdentityInfoConsumer {

    private final DeviceIdentityCache cache;

    private final DeviceInformationMapper mapper;

    public DeviceIdentityInfoConsumer(DeviceIdentityCache cache, DeviceInformationMapper mapper) {
        this.cache = cache;
        this.mapper = mapper;
    }

    @RabbitListener(queues = "#{queueNamingService.getDeviceIdentityInfoQueueName()}")
    public void receiveUpdate(DeviceIdentityDTOImpl in) {
        cache.update(mapper.dtoToDomain(in));
    }
}
