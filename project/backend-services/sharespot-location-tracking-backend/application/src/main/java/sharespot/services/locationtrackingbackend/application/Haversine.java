package sharespot.services.locationtrackingbackend.application;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;

import java.util.List;
import java.util.stream.IntStream;

public class Haversine {
    private static final double R = 6372.8; // In kilometers

    public static boolean isMoving(ProcessedSensorDataWithRecordsDTO center, List<ProcessedSensorDataWithRecordsDTO> gpsData, Double distanceInKm) {
        return gpsData.stream().anyMatch(point -> calcHaversine(point, center) > distanceInKm);
    }

    public static double calcDistance(List<ProcessedSensorDataWithRecordsDTO> gpsData) {
        if (gpsData.size() < 2) return 0;
        return IntStream.range(1, gpsData.size()).mapToDouble(i -> calcHaversine(gpsData.get(i - 1), gpsData.get(i))).sum();
    }

    public static double calcHaversine(ProcessedSensorDataWithRecordsDTO first, ProcessedSensorDataWithRecordsDTO second) {
        return haversine(first.data.gps.latitude, first.data.gps.longitude, second.data.gps.latitude, second.data.gps.longitude);
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
