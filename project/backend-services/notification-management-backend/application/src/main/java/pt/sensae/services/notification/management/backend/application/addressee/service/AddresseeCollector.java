package pt.sensae.services.notification.management.backend.application.addressee.service;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.addressee.mapper.AddresseeDTOMapper;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;
import pt.sensae.services.notification.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.notification.management.backend.application.auth.TokenExtractor;
import pt.sensae.services.notification.management.backend.application.auth.UnauthorizedException;
import pt.sensae.services.notification.management.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeRepository;

import java.util.HashSet;

@Service
public class AddresseeCollector {

    private final TokenExtractor authHandler;

    private final AddresseeRepository repository;

    private final AddresseeDTOMapper mapper;

    public AddresseeCollector(TokenExtractor authHandler, AddresseeRepository repository, AddresseeDTOMapper mapper) {
        this.authHandler = authHandler;
        this.repository = repository;
        this.mapper = mapper;
    }

    public AddresseeDTO fetch(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("notification_management:config:read"))
            throw new UnauthorizedException("No Permissions");

        var id = AddresseeId.of(extract.oid);
        var addressee = repository.findById(id);
        return mapper.toDto(addressee);
    }
}
