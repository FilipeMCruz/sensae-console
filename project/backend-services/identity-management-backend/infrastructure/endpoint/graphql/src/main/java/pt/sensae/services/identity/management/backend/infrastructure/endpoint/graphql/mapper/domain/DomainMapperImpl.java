package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.mapper.domain;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.mapper.domain.DomainMapper;
import pt.sensae.services.identity.management.backend.application.model.domain.*;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.ChangeDomainCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.CreateDomainCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.DomainResult;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.ViewDomainQuery;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain.*;

import java.util.UUID;
import java.util.stream.Stream;

@Service
public class DomainMapperImpl implements DomainMapper {

    @Override
    public ChangeDomainCommand dtoToCommand(ChangeDomainDTO dto) {
        var info = (ChangeDomainDTOImpl) dto;
        var command = new ChangeDomainCommand();
        command.domainId = UUID.fromString(info.domainId);
        command.domainName = info.domainName;
        command.permissions = info.permissions;
        return command;
    }

    @Override
    public CreateDomainCommand dtoToCommand(CreateDomainDTO dto) {
        var info = (CreateDomainDTOImpl) dto;
        var command = new CreateDomainCommand();
        command.domainId = UUID.randomUUID();
        command.domainName = info.newDomainName;
        command.parentDomainId = UUID.fromString(info.parentDomainOid);
        return command;
    }

    @Override
    public ViewDomainQuery dtoToCommand(ViewDomainDTO dto) {
        var info = (ViewDomainDTOImpl) dto;
        var viewDomainQuery = new ViewDomainQuery();
        viewDomainQuery.topDomainId = UUID.fromString(info.oid);
        return viewDomainQuery;
    }

    @Override
    public DomainDTO resultToDto(DomainResult result) {
        var domainDTO = new DomainDTOImpl();
        domainDTO.name = result.domainName;
        domainDTO.oid = result.domainId.toString();
        domainDTO.path = result.path;
        domainDTO.permissions = result.permissions;
        return domainDTO;
    }

    @Override
    public Stream<DomainIdDTO> resultToSimpleDto(DomainResult result) {
        return result.path.stream().map(d -> {
            var domainIdDTO = new DomainIdDTOImpl();
            domainIdDTO.oid = d;
            return domainIdDTO;
        });
    }
}
