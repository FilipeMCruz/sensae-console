package pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.dispatcher.backend.domain.tenant.TenantRepository;
import pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.mapper.TenantIdentityMapper;
import pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.model.TenantIdentityDTO;

@Service
public class TenantIdentityInfoConsumer {

    private final TenantRepository cache;

    public static final String QUEUE = "internal.notification.dispatcher.tenant.info.queue";

    public TenantIdentityInfoConsumer(TenantRepository cache) {
        this.cache = cache;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(TenantIdentityDTO in) {
        cache.index(TenantIdentityMapper.dtoToDomain(in));
    }
}
