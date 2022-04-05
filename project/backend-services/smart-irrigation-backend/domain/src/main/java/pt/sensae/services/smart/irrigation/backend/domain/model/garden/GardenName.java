package pt.sensae.services.smart.irrigation.backend.domain.model.garden;

public record GardenName(String value) {

    public static GardenName of(String name) {
        return new GardenName(name);
    }
}
