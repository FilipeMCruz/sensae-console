package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain;

import pt.sensae.services.identity.management.backend.application.model.domain.DomainIdDTO;

import java.util.Objects;

public class DomainIdDTOImpl implements DomainIdDTO {
    public String oid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainIdDTOImpl that = (DomainIdDTOImpl) o;

        return Objects.equals(oid, that.oid);
    }

    @Override
    public int hashCode() {
        return oid != null ? oid.hashCode() : 0;
    }
}
