package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.mapper.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.domain.DomainMapper;
import sharespot.services.identitymanagementbackend.application.model.domain.CreateDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.ViewDomainDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.CreateDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.DomainResult;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.ViewDomainQuery;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain.CreateDomainDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain.DomainDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain.ViewDomainDTOImpl;

import java.util.UUID;

@Service
public class DomainMapperImpl implements DomainMapper {

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
        return domainDTO;
    }
}
