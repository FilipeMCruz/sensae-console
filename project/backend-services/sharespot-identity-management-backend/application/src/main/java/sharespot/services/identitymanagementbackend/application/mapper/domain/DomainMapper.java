package sharespot.services.identitymanagementbackend.application.mapper.domain;

import sharespot.services.identitymanagementbackend.application.model.domain.CreateDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.CreateDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.DomainResult;

public interface DomainMapper {

    CreateDomainCommand dtoToCommand(CreateDomainDTO dto);

    DomainDTO resultToDto(DomainResult result);
}
