package pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone;

import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotValidException;
import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record Area(Set<BoundaryPoint> boundaries) {

    public Area {
        if (boundaries.size() < 3) {
            throw new NotValidException("An Area needs at least 3 vertex");
        }
        if (!positionsAreValid(boundaries.stream().map(BoundaryPoint::position).collect(Collectors.toList()))) {
            throw new NotValidException("Given Boundaries are missing some points");
        }
    }

    /**
     * Check if given point is inside an area.
     * <p>
     * This algo was implemented according to @see <a href="https://alienryderflex.com/polygon/"></a>.
     * <p>
     * Using the ray casting algo adapted from @see <a href="https://rosettacode.org/wiki/Ray-casting_algorithm#Java"></a>.
     *
     * @param point coordinate to be checked
     * @return true is the point is inside the area and false if not
     */
    public boolean contains(GPSPoint point) {
        boolean inside = false;
        int len = boundaries.size();
        GPSPoint[] shape = new GPSPoint[len];
        boundaries.forEach(p -> shape[p.position()] = p.point());
        for (int i = 0; i < len; i++) {
            if (intersects(shape[i], shape[(i + 1) % len], point)) inside = !inside;
        }
        return inside;
    }

    private boolean intersects(GPSPoint vertexA, GPSPoint vertexB, GPSPoint point) {
        var pointLong = point.longitude();
        var pointLat = point.latitude();

        if (vertexA.longitude() > vertexB.longitude()) return intersects(vertexB, vertexA, point);

        if (pointLong.equals(vertexA.longitude()) || pointLong.equals(vertexB.longitude())) pointLong += (float) 0.0001;

        if (pointLong > vertexB.longitude() || pointLong < vertexA.longitude() || pointLat >= Math.max(vertexA.latitude(), vertexB.latitude()))
            return false;

        if (pointLat < Math.min(vertexA.latitude(), vertexB.latitude())) return true;

        double red = (pointLong - vertexA.longitude()) / (pointLat - vertexA.latitude());
        double blue = (vertexB.longitude() - vertexA.longitude()) / (vertexB.latitude() - vertexA.latitude());
        return red >= blue;
    }

    private boolean positionsAreValid(List<Integer> arrA) {
        var checker = new ArrayList<Boolean>();
        for (Integer integer : arrA) {
            if (integer >= arrA.size() || integer < 0) {
                return false;
            }
        }
        arrA.forEach(a -> checker.add(false));
        arrA.forEach(a -> checker.set(a, !checker.get(a)));

        return checker.stream().allMatch(s -> s);
    }
}
