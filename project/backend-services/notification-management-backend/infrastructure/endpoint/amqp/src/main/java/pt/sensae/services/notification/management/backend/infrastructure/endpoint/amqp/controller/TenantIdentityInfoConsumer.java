package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.domain.tenant.TenantCache;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.mapper.TenantIdentityMapper;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.model.TenantIdentityDTO;

@Service
public class TenantIdentityInfoConsumer {

    private final TenantCache cache;

    public static final String QUEUE = "internal.notification.management.tenant.info.queue";

    public TenantIdentityInfoConsumer(TenantCache cache) {
        this.cache = cache;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(TenantIdentityDTO in) {
        cache.index(TenantIdentityMapper.dtoToDomain(in));
    }
}
