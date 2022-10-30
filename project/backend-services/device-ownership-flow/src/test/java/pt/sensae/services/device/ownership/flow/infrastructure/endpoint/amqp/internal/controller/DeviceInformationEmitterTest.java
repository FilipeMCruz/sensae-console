package pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.internal.controller;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.providers.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.providers.connectors.InMemorySink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.sensae.services.device.ownership.flow.application.RoutingKeysProvider;
import pt.sensae.services.device.ownership.flow.application.model.DeviceIdDTO;
import pt.sensae.services.device.ownership.flow.application.model.DeviceTopicMessage;
import pt.sensae.services.device.ownership.flow.domain.DeviceId;
import pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.inject.Any;
import javax.inject.Inject;

import java.util.UUID;

@QuarkusTest
class DeviceInformationEmitterTest {

    @Inject
    DeviceInformationEmitter emitter;

    @Inject
    RoutingKeysProvider provider;

    @Inject
    @Any
    InMemoryConnector connector;

    @Test
    void testEmitterCanReachRabbitMQ() {
        var unknown = provider
                .getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
                .withOperationType(OperationTypeOptions.UNKNOWN)
                .build().orElseThrow();

        var deviceId = DeviceId.of(UUID.randomUUID());
        
        emitter.next(new DeviceTopicMessage(deviceId, unknown));

        var payload = connector.sink("egress-device-ownership")
                .received().get(0).getPayload();
        
        Assertions.assertNotNull(payload);

        InMemorySink<DeviceIdDTO> queue = connector.sink("egress-device-ownership");
        DeviceIdDTO dto = queue.received().get(0).getPayload();
        Assertions.assertEquals(deviceId.value().toString(), ((DeviceIdDTOImpl) dto).id);
    }
}
