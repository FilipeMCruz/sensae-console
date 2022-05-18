package sharespot.services.identitymanagementbackend.application.mapper.domain;

import sharespot.services.identitymanagementbackend.application.model.domain.*;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.ChangeDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.CreateDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.DomainResult;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.ViewDomainQuery;

import java.util.Set;
import java.util.stream.Stream;

public interface DomainMapper {

    ChangeDomainCommand dtoToCommand(ChangeDomainDTO dto);

    CreateDomainCommand dtoToCommand(CreateDomainDTO dto);

    ViewDomainQuery dtoToCommand(ViewDomainDTO dto);

    DomainDTO resultToDto(DomainResult result);
    Stream<DomainIdDTO> resultToSimpleDto(DomainResult result);
}
