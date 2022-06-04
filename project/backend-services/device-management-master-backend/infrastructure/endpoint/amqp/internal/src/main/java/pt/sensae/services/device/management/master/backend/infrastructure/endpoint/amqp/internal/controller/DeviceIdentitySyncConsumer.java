package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.ownership.DeviceIdentityCache;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.mapper.DeviceMapper;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.DeviceIdentityDTOImpl;

import java.util.Set;

@Service
public class DeviceIdentitySyncConsumer {

    private final DeviceIdentityCache cache;

    private final DeviceMapper mapper;

    public static final String QUEUE = "internal.device.management.sync.queue";

    public DeviceIdentitySyncConsumer(DeviceIdentityCache cache, DeviceMapper mapper) {
        this.cache = cache;
        this.mapper = mapper;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Set<DeviceIdentityDTOImpl> in) {
        cache.refresh(in.stream().map(mapper::dtoToDomain));
    }
}
