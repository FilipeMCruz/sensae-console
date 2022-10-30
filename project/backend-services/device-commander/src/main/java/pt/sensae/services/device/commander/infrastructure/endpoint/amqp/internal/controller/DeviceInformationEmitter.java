package pt.sensae.services.device.commander.infrastructure.endpoint.amqp.internal.controller;

import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import pt.sensae.services.device.commander.application.DeviceInformationNotificationPublisher;
import pt.sensae.services.device.commander.application.model.DeviceTopicMessage;
import pt.sensae.services.device.commander.infrastructure.endpoint.amqp.internal.mapper.DeviceMapper;
import pt.sensae.services.device.commander.infrastructure.endpoint.amqp.internal.model.DeviceDTOImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DeviceInformationEmitter implements DeviceInformationNotificationPublisher {

    @Channel("egress-device-informations")
    Emitter<DeviceDTOImpl> emitter;

    @Inject
    DeviceMapper deviceMapper;

    @Override
    public void next(DeviceTopicMessage sensorTypeTopicMessage) {
        var metadata = Metadata.of(new OutgoingRabbitMQMetadata.Builder()
                .withRoutingKey(sensorTypeTopicMessage.keys().toString())
                .build());

        var message = Message.of(deviceMapper.domainToDto(sensorTypeTopicMessage.device()), metadata);

        emitter.send(message);
    }
}
