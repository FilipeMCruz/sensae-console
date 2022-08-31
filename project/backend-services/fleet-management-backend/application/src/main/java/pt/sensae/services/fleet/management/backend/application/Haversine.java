package pt.sensae.services.fleet.management.backend.application;

import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sensae.services.fleet.management.backend.domain.model.GPSDataDetails;

import java.util.List;
import java.util.stream.IntStream;

public class Haversine {
    private static final double R = 6372.8; // In kilometers

    public static boolean isMoving(DataUnitDTO center, List<DataUnitDTO> gpsData, Double distanceInKm) {
        return gpsData.stream().anyMatch(point -> calcHaversine(point, center) > distanceInKm);
    }

    public static double calcDistance(List<DataUnitDTO> gpsData) {
        if (gpsData.size() < 2) return 0;
        return IntStream.range(1, gpsData.size())
                .mapToDouble(i -> calcHaversine(gpsData.get(i - 1), gpsData.get(i)))
                .sum();
    }

    public static double calcHaversine(GPSDataDetails first, GPSDataDetails second) {
        return haversine(first.latitude(), first.longitude(), second.latitude(), second.longitude());
    }

    public static double calcHaversine(DataUnitDTO first, DataUnitDTO second) {
        return haversine(first.getSensorData().gps.latitude, first.getSensorData().gps.longitude, second.getSensorData().gps.latitude, second.getSensorData().gps.longitude);
    }

    private static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
}
