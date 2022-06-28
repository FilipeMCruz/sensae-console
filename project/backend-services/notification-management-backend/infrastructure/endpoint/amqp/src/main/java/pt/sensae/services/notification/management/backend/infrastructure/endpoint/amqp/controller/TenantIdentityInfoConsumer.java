package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.domain.tenant.TenantRepository;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.mapper.TenantIdentityMapper;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.model.TenantIdentityDTO;

@Service
public class TenantIdentityInfoConsumer {

    private final TenantRepository cache;

    public TenantIdentityInfoConsumer(TenantRepository cache) {
        this.cache = cache;
    }

    @RabbitListener(queues = "#{queueNamingService.getTenantIdentityInfoQueueName()}")
    public void receiveInfo(TenantIdentityDTO in) {
        cache.index(TenantIdentityMapper.dtoToDomain(in));
    }
}
