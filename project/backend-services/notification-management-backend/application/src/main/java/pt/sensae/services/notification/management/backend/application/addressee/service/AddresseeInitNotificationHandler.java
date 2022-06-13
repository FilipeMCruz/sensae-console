package pt.sensae.services.notification.management.backend.application.addressee.service;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.addressee.mapper.InternalAddresseeDTOMapper;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeRepository;

@Service
public class AddresseeInitNotificationHandler {

    private final AddresseeRepository collector;

    private final AddresseeSyncHandler handler;

    private final InternalAddresseeDTOMapper mapper;

    public AddresseeInitNotificationHandler(AddresseeRepository collector, AddresseeSyncHandler handler, InternalAddresseeDTOMapper mapper) {
        this.collector = collector;
        this.handler = handler;
        this.mapper = mapper;
    }

    public void publishCurrentState() {
        var collect = collector.findAll();
        handler.publishState(collect.map(mapper::domainToDto));
    }
}
