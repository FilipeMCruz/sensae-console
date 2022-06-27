package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.identity;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domain.model.identity.DeviceIdentityRepository;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.mapper.DeviceInformationMapper;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.identity.DeviceIdentityDTOImpl;

import java.util.Set;

@Service
public class DeviceIdentitySyncConsumer {

    private final DeviceIdentityRepository cache;

    private final DeviceInformationMapper mapper;

    public DeviceIdentitySyncConsumer(DeviceIdentityRepository cache, DeviceInformationMapper mapper) {
        this.cache = cache;
        this.mapper = mapper;
    }

    @RabbitListener(queues = "#{queueNamingService.getDeviceIdentitySyncQueueName()}")
    public void receiveUpdate(Set<DeviceIdentityDTOImpl> in) {
        cache.refresh(in.stream().map(mapper::dtoToDomain));
    }
}
