package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.device;

import pt.sensae.services.identity.management.backend.application.model.device.ExpelDeviceFromDomainDTO;

public class ExpelDeviceFromDomainDTOImpl implements ExpelDeviceFromDomainDTO {
    public String deviceOid;
    public String domainOid;
}
