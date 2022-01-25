package sharespot.services.datavalidator.application;

public class BoundingBox {
    
    private final String name;
    private final double north;
    private final double south;
    private final double west;
    private final double east;

    public BoundingBox(String name, double north, double south, double west, double east) {
        this.name = name;
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
    }

    public boolean isInside(double latitude, double longitude) {
        return longitude < north && longitude > south && latitude > west && latitude < east;
    }
}
