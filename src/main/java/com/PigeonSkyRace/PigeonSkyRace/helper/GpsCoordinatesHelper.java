package com.PigeonSkyRace.PigeonSkyRace.helper;

public class GpsCoordinatesHelper {
    public static double getLatitude(String Coordinates) {
        String[] coordinates = Coordinates.split(",");
        return Double.parseDouble(coordinates[0]);
    }
    public static double getLongitude(String Coordinates) {
        String[] coordinates = Coordinates.split(",");
        return Double.parseDouble(coordinates[1]);
    }
}
