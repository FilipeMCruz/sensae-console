package pt.sensae.services.identity.management.backend.application.internal.identity.tenant;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.Tenant;
import pt.sensae.services.identity.management.backend.domainservices.service.tenant.TenantUpdateEventPublisher;

import javax.annotation.PostConstruct;

@Service
public class TenantNotificationPublisher implements TenantUpdateEventPublisher {

    private FluxSink<TenantIdentityDTO> dataStream;

    private ConnectableFlux<TenantIdentityDTO> dataPublisher;

    private final TenantIdentityMapper mapper;

    public TenantNotificationPublisher(TenantIdentityMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        Flux<TenantIdentityDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<TenantIdentityDTO> getSinglePublisher() {
        return dataPublisher;
    }

    @Override
    public void publishUpdate(Tenant domain) {
        dataStream.next(mapper.domainToDto(domain));
    }
}
