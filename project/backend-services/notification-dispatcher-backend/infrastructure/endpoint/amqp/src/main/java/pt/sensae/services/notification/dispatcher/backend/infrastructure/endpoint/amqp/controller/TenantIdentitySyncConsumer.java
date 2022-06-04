package pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.dispatcher.backend.domain.tenant.TenantRepository;
import pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.mapper.TenantIdentityMapper;
import pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.model.TenantIdentityDTO;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TenantIdentitySyncConsumer {

    private final TenantRepository cache;

    public static final String QUEUE = "internal.notification.dispatcher.tenant.sync.queue";

    public TenantIdentitySyncConsumer(TenantRepository cache) {
        this.cache = cache;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Set<TenantIdentityDTO> in) {
        cache.refresh(in.stream().map(TenantIdentityMapper::dtoToDomain).collect(Collectors.toSet()));
    }
}
