package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;


public record GardeningArea(GardeningAreaId id, GardenName name, Area area, Owners owners) {
    public boolean isOwner(Owners domains) {
        return owners.hasAny(domains);
    }

    public GardeningArea withOwners(Owners newOwners) {
        return new GardeningArea(id, name, area, newOwners);
    }
}
