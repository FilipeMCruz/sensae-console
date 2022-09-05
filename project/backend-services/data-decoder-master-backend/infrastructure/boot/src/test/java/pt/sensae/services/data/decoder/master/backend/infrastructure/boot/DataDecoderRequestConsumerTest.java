package pt.sensae.services.data.decoder.master.backend.infrastructure.boot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import pt.sensae.services.data.decoder.master.backend.application.DataDecoderHandlerService;
import pt.sensae.services.data.decoder.master.backend.application.RoutingKeysProvider;
import pt.sensae.services.data.decoder.master.backend.domain.DataDecoder;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeScript;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.DataDecoderNotificationDTOImpl;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.DataDecoderNotificationTypeDTOImpl;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.SensorTypeIdDTOImpl;
import pt.sensae.services.data.decoder.master.backend.infrastructure.persistence.memory.LastTimeSeenDecoderCache;
import pt.sensae.services.data.decoder.master.backend.infrastructure.persistence.postgres.DataDecodersRepositoryImpl;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import java.sql.SQLException;

public class DataDecoderRequestConsumerTest extends IntegrationTest {

    @Autowired
    DataDecoderHandlerService publisher;

    @Autowired
    RabbitTemplate amqpTemplate;

    @Autowired
    LastTimeSeenDecoderCache cache;

    @Autowired
    RoutingKeysProvider provider;

    @Autowired
    RabbitAdmin rabbitAdmin;

    @Autowired
    DataDecoderHandlerService service;

    @Autowired
    DataDecodersRepositoryImpl repository;

    @BeforeEach
    public void init() {
        if (rabbitAdmin.getQueueInfo("integration-test-unknown") == null) {
            var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                    .withContextType(ContextTypeOptions.DATA_DECODER)
                    .withContainerType(ContainerTypeOptions.DATA_DECODER)
                    .withOperationType(OperationTypeOptions.INFO).build().orElseThrow();

            var queue = QueueBuilder.durable("integration-test-unknown").build();
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue)
                    .to(new TopicExchange(IoTCoreTopic.INTERNAL_EXCHANGE))
                    .with(keys.toString()));
        }
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        performQuery("TRUNCATE decoder");
    }

    @Test
    public void ensureDecoderRequestIsProcessedAsExpectedWhenDecoderCantBeFound() throws Exception {
        var routingKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContextType(ContextTypeOptions.DATA_DECODER)
                .withContainerType(ContainerTypeOptions.DATA_DECODER)
                .withOperationType(OperationTypeOptions.UNKNOWN)
                .build()
                .orElseThrow();

        var request = new SensorTypeIdDTOImpl();
        request.sensorType = "lgt92";
        amqpTemplate.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, routingKeys.toString(), request);

        Thread.sleep(200);

        var lgt92 = cache.find(SensorTypeId.of("lgt92"));
        Assertions.assertTrue(lgt92.isPresent());
    }

    @Test
    public void ensureDecoderUnknownIsProcessedAsExpectedWhenDecoderCanBeFound() throws Exception {
        repository.save(new DataDecoder(SensorTypeId.of("lgt92"), SensorTypeScript.of("ascm")));

        var routingKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContextType(ContextTypeOptions.DATA_DECODER)
                .withContainerType(ContainerTypeOptions.DATA_DECODER)
                .withOperationType(OperationTypeOptions.UNKNOWN)
                .build()
                .orElseThrow();

        var request = new SensorTypeIdDTOImpl();
        request.sensorType = "lgt92";
        amqpTemplate.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, routingKeys.toString(), request);

        Thread.sleep(200);

        var dto = (DataDecoderNotificationDTOImpl) amqpTemplate.receiveAndConvert("integration-test-unknown");
        Assertions.assertEquals(DataDecoderNotificationTypeDTOImpl.UPDATE, dto.type);
        Assertions.assertEquals("lgt92", dto.sensorType);
        Assertions.assertEquals("ascm", dto.information.script);

        var lgt92 = cache.find(SensorTypeId.of("lgt92"));
        Assertions.assertTrue(lgt92.isPresent());
    }
}
