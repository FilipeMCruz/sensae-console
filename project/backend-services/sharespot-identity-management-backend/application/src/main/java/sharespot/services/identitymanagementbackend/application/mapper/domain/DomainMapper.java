package sharespot.services.identitymanagementbackend.application.mapper.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.CreateDomainCommand;

@Service
public interface DomainMapper {

    CreateDomainCommand dtoToCommand(DomainDTO dto);
}
