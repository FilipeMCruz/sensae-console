package pt.sensae.services.data.decoder.master.backend.infrastructure.boot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpIOException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import pt.sensae.services.data.decoder.master.backend.application.DataDecoderHandlerService;
import pt.sensae.services.data.decoder.master.backend.domain.DataDecoder;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeScript;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.DataDecoderNotificationDTOImpl;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.DataDecoderNotificationTypeDTOImpl;
import pt.sharespot.iot.core.IoTCoreTopic;

public class DataDecoderInfoEmitterTest extends IntegrationTest {

    @Autowired
    DataDecoderHandlerService publisher;

    @Autowired
    RabbitAdmin rabbitAdmin;

    @Autowired
    RabbitTemplate amqpTemplate;

    @AfterEach
    public void init() {
        var queue = QueueBuilder.durable("integration-test").build();
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue)
                .to(new TopicExchange(IoTCoreTopic.INTERNAL_EXCHANGE))
                .with("#"));
    }

    //Here because spring amqp fails to register queues and binding before the first message is sent
    @Test
    public void smokeTest() {
        publisher.publishDelete(SensorTypeId.of("lgt92"));
        Assertions.assertThrows(AmqpIOException.class, () -> amqpTemplate.receiveAndConvert("integration-test"));
    }

    @Test
    public void ensureDeletedDecoderIsSentAsExpected() {
        publisher.publishDelete(SensorTypeId.of("lgt92"));

        var dto = (DataDecoderNotificationDTOImpl) amqpTemplate.receiveAndConvert("integration-test");
        Assertions.assertEquals(DataDecoderNotificationTypeDTOImpl.DELETE, dto.type);
        Assertions.assertEquals("lgt92", dto.sensorType);
    }

    @Test
    public void ensureNewDecoderIsSentAsExpected() {
        publisher.publishUpdate(new DataDecoder(SensorTypeId.of("lgt92"), SensorTypeScript.of("asmc")));

        var dto = (DataDecoderNotificationDTOImpl) amqpTemplate.receiveAndConvert("integration-test");
        Assertions.assertEquals(DataDecoderNotificationTypeDTOImpl.UPDATE, dto.type);
        Assertions.assertEquals("lgt92", dto.sensorType);
        Assertions.assertEquals("asmc", dto.information.script);
    }
}
