package sharespot.services.identitymanagementbackend.application.mapper.domain;

import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.CreateDomainCommand;

public interface DomainMapper {

    CreateDomainCommand dtoToCommand(DomainDTO dto);
}
