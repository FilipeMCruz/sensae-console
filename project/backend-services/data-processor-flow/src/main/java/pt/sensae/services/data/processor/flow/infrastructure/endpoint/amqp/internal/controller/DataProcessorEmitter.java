package pt.sensae.services.data.processor.flow.infrastructure.endpoint.amqp.internal.controller;

import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import pt.sensae.services.data.processor.flow.application.DataDecoderNotificationPublisher;
import pt.sensae.services.data.processor.flow.application.mapper.DataTransformationEventMapper;
import pt.sensae.services.data.processor.flow.application.model.SensorTypeIdDTO;
import pt.sensae.services.data.processor.flow.application.model.SensorTypeTopicMessage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DataProcessorEmitter implements DataDecoderNotificationPublisher {

    @Channel("egress-data-processors")
    Emitter<SensorTypeIdDTO> emitter;

    @Inject
    DataTransformationEventMapper mapper;

    @Override
    public void next(SensorTypeTopicMessage sensorTypeTopicMessage) {
        var metadata = Metadata.of(new OutgoingRabbitMQMetadata.Builder()
                .withRoutingKey(sensorTypeTopicMessage.keys().toString())
                .build());

        var message = Message.of(mapper.domainToDto(sensorTypeTopicMessage.type()), metadata);

        emitter.send(message);
    }
}
