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
import sharespot.services.identitymanagementbackend.application.internal.tenant.TenantNotificationPublisher;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.mapper.TenantIdentityMapperImpl;

import javax.annotation.PostConstruct;

@Service
public class TenantIdentityInfoEmitter {

    private final AmqpTemplate template;
    private final TenantNotificationPublisher publisher;

    private final InternalRoutingKeys infoKeys;

    public TenantIdentityInfoEmitter(RoutingKeysProvider provider, @Qualifier("amqpTemplate") AmqpTemplate template, TenantNotificationPublisher publisher) {
        this.template = template;
        this.publisher = publisher;
        var infoKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withContextType(ContextTypeOptions.TENANT_IDENTITY)
                .withOperationType(OperationTypeOptions.INFO)
                .build();
        if (infoKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.infoKeys = infoKeys.get();
    }


    @PostConstruct
    public void init() {
        publisher.getSinglePublisher()
                .subscribe(tenant -> template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, infoKeys.toString(),
                        tenant));
    }
}
