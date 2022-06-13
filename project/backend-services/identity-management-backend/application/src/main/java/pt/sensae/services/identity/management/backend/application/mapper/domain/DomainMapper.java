package pt.sensae.services.identity.management.backend.application.mapper.domain;

import pt.sensae.services.identity.management.backend.application.model.domain.*;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.ChangeDomainCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.CreateDomainCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.DomainResult;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.ViewDomainQuery;

import java.util.stream.Stream;

public interface DomainMapper {

    ChangeDomainCommand dtoToCommand(ChangeDomainDTO dto);

    CreateDomainCommand dtoToCommand(CreateDomainDTO dto);

    ViewDomainQuery dtoToCommand(ViewDomainDTO dto);

    DomainDTO resultToDto(DomainResult result);
    Stream<DomainIdDTO> resultToSimpleDto(DomainResult result);
}
