package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.identity.tenant;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sensae.services.identity.management.backend.application.RoutingKeysProvider;
import pt.sensae.services.identity.management.backend.application.internal.identity.tenant.TenantIdentityDTO;
import pt.sensae.services.identity.management.backend.application.internal.identity.tenant.TenantIdentitySyncHandler;

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
    public void publishState(Stream<TenantIdentityDTO> tenants) {
        template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, syncKeys.toString(),
                tenants.collect(Collectors.toSet()));
    }
}
