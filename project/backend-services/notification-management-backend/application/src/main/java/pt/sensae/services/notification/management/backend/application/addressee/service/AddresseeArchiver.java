package pt.sensae.services.notification.management.backend.application.addressee.service;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.addressee.mapper.AddresseeCommandMapper;
import pt.sensae.services.notification.management.backend.application.addressee.mapper.AddresseeDTOMapper;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeConfigUpdateCommand;
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

    public AddresseeArchiver(TokenExtractor authHandler,
                             AddresseeRepository repository,
                             AddresseeDTOMapper mapper,
                             AddresseeCommandMapper commandMapper) {
        this.authHandler = authHandler;
        this.repository = repository;
        this.mapper = mapper;
        this.commandMapper = commandMapper;
    }

    public AddresseeDTO edit(AddresseeConfigUpdateCommand command, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("alert_management:config:edit"))
            throw new UnauthorizedException("No Permissions");

        var index = commandMapper.toDomain(command, AddresseeId.of(extract.oid));
        var addressee = repository.index(index);
        return mapper.toDto(addressee);
    }
}
