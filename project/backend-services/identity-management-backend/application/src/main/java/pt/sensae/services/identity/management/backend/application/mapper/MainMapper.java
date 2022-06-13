package pt.sensae.services.identity.management.backend.application.mapper;

import pt.sensae.services.identity.management.backend.application.model.domain.DomainInfoDTO;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.DomainInfoResult;

public interface MainMapper {

    DomainInfoDTO resultToDto(DomainInfoResult result);
}
