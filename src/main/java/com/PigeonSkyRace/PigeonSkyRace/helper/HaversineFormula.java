package com.PigeonSkyRace.PigeonSkyRace.helper;

public class HaversineFormula {
    public static double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
