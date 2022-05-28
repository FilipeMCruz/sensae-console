package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.domain.tenant.TenantCache;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.mapper.TenantIdentityMapper;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.model.TenantIdentityDTO;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TenantIdentitySyncConsumer {

    private final TenantCache cache;

    public static final String QUEUE = "internal.notification.management.sync.queue";

    public TenantIdentitySyncConsumer(TenantCache cache) {
        this.cache = cache;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Set<TenantIdentityDTO> in) {
        cache.refresh(in.stream().map(TenantIdentityMapper::dtoToDomain).collect(Collectors.toSet()));
    }
}
