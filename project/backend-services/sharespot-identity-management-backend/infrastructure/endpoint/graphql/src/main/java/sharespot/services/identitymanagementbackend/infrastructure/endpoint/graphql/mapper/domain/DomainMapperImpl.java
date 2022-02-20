package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.mapper.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.domain.DomainMapper;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.CreateDomainCommand;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain.DomainDTOImpl;

@Service
public class DomainMapperImpl implements DomainMapper {

    @Override
    public CreateDomainCommand dtoToCommand(DomainDTO dto) {
        var info = (DomainDTOImpl) dto;
        var command = new CreateDomainCommand();
        command.domainId = info.newDomainId;
        command.domainName = info.newDomainName;
        command.parentDomainId = info.parentDomainId;
        return command;
    }
}
