package sharespot.services.identitymanagementbackend.application.mapper;

import sharespot.services.identitymanagementbackend.application.model.domain.DomainInfoDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.DomainInfoResult;

public interface MainMapper {

    DomainInfoDTO resultToDto(DomainInfoResult result);
}
