package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import sharespot.services.identitymanagementbackend.application.RoutingKeysProvider;
import sharespot.services.identitymanagementbackend.application.internal.tenant.TenantIdentitySyncHandler;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.Tenant;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.mapper.TenantIdentityMapper;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TenantIdentitySyncEmitter implements TenantIdentitySyncHandler {

    private final AmqpTemplate template;

    private final InternalRoutingKeys syncKeys;

    public TenantIdentitySyncEmitter(RoutingKeysProvider provider, @Qualifier("amqpTemplate") AmqpTemplate template) {
        this.template = template;
        var syncKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withContextType(ContextTypeOptions.TENANT_IDENTITY)
                .withOperationType(OperationTypeOptions.SYNC)
                .build();
        if (syncKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.syncKeys = syncKeys.get();
    }


    @Override
    public void publishState(Stream<Tenant> tenants) {
        template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, syncKeys.toString(),
                tenants.map(TenantIdentityMapper::domainToDto).collect(Collectors.toSet()));
    }
}
