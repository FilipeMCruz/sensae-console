package pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.dispatcher.backend.domain.adressee.AddresseeRepository;
import pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.mapper.AddresseeMapper;
import pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.model.AddresseeDTO;

@Service
public class AddresseeInfoConsumer {

    private final AddresseeRepository cache;

    public AddresseeInfoConsumer(AddresseeRepository cache) {
        this.cache = cache;
    }

    @RabbitListener(queues = "#{queueNamingService.getAttendeesConfigurationInfoQueueName()}")
    public void receiveUpdate(AddresseeDTO in) {
        cache.index(AddresseeMapper.dtoToDomain(in));
    }
}
