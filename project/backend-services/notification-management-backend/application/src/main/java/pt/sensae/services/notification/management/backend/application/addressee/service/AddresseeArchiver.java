package pt.sensae.services.notification.management.backend.application.addressee.service;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.addressee.mapper.AddresseeCommandMapper;
import pt.sensae.services.notification.management.backend.application.addressee.mapper.AddresseeDTOMapper;
import pt.sensae.services.notification.management.backend.application.addressee.mapper.InternalAddresseeDTOMapper;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeConfigUpdateCommandDTO;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;
import pt.sensae.services.notification.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.notification.management.backend.application.auth.TokenExtractor;
import pt.sensae.services.notification.management.backend.application.auth.UnauthorizedException;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeRepository;

@Service
public class AddresseeArchiver {

    private final TokenExtractor authHandler;

    private final AddresseeRepository repository;

    private final AddresseeDTOMapper mapper;

    private final AddresseeCommandMapper commandMapper;

    private final AddresseeInfoHandler eventHandler;

    private final InternalAddresseeDTOMapper eventMapper;

    public AddresseeArchiver(TokenExtractor authHandler,
                             AddresseeRepository repository,
                             AddresseeDTOMapper mapper,
                             AddresseeCommandMapper commandMapper,
                             AddresseeInfoHandler eventHandler,
                             InternalAddresseeDTOMapper eventMapper) {
        this.authHandler = authHandler;
        this.repository = repository;
        this.mapper = mapper;
        this.commandMapper = commandMapper;
        this.eventHandler = eventHandler;
        this.eventMapper = eventMapper;
    }

    public AddresseeDTO edit(AddresseeConfigUpdateCommandDTO command, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("notification_management:config:edit"))
            throw new UnauthorizedException("No Permissions");

        var index = commandMapper.toDomain(command, AddresseeId.of(extract.oid));
        var addressee = repository.index(index);

        eventHandler.publish(eventMapper.domainToDto(addressee));

        return mapper.toDto(addressee);
    }
}
