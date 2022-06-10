package pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone;


public record IrrigationZone(IrrigationZoneId id, IrrigationZoneName name, Area area, Owners owners) {
    public boolean isOwner(Owners domains) {
        return owners.hasAny(domains);
    }

    public IrrigationZone withOwners(Owners newOwners) {
        return new IrrigationZone(id, name, area, newOwners);
    }
}
