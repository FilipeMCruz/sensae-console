package pt.sensae.services.data.decoder.master.backend.infrastructure.boot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import pt.sensae.services.data.decoder.master.backend.application.RoutingKeysProvider;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.SensorTypeIdDTOImpl;
import pt.sensae.services.data.decoder.master.backend.infrastructure.persistence.memory.LastTimeSeenDecoderCache;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

public class DataDecoderPingConsumerTest extends IntegrationTest {

    @Autowired
    RabbitTemplate amqpTemplate;

    @Autowired
    LastTimeSeenDecoderCache cache;

    @Autowired
    RoutingKeysProvider provider;

    @Test
    public void ensureDecoderPingIsProcessedAsExpected() throws InterruptedException {
        var routingKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContextType(ContextTypeOptions.DATA_DECODER)
                .withContainerType(ContainerTypeOptions.DATA_DECODER)
                .withOperationType(OperationTypeOptions.PING).build();

        Assertions.assertTrue(routingKeys.isPresent());

        var ping = new SensorTypeIdDTOImpl();
        ping.sensorType = "lgt92";
        amqpTemplate.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, routingKeys.get().toString(), ping);

        Thread.sleep(200);

        var lgt92 = cache.find(SensorTypeId.of("lgt92"));
        Assertions.assertTrue(lgt92.isPresent());
    }
}
