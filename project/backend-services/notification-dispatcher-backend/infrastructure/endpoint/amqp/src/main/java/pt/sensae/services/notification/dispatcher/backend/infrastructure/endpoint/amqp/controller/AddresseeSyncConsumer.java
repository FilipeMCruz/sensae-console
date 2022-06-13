package pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.dispatcher.backend.domain.adressee.AddresseeRepository;
import pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.mapper.AddresseeMapper;
import pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.model.AddresseeDTO;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AddresseeSyncConsumer {

    private final AddresseeRepository cache;

    public static final String QUEUE = "internal.notification.dispatcher.addressee.sync.queue";

    public AddresseeSyncConsumer(AddresseeRepository cache) {
        this.cache = cache;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Set<AddresseeDTO> in) {
        cache.refresh(in.stream().map(AddresseeMapper::dtoToDomain).collect(Collectors.toSet()));
    }
}
