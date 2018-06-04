package ch.supsi.dti.i2b.shrug.optitravel.geography;

public class Deg2Meter {

    private static double deg2rad(double deg) {
        return (deg / 360.0) * 2.0 * Math.PI;
    }

    public static double lng(double lat){
        // X: E - W
        return 111412.84 * Math.cos(deg2rad(lat)) - 93.5 * Math.cos(deg2rad(3.0 * lat))
                + 0.118 * Math.cos(deg2rad(5.0 * lat));
    }

    public static double lat(double lat) {
        // Y: N - S
        return 111132.92 - 559.82 * Math.cos(deg2rad(2.0 * lat)) + 1.175 * Math.cos(deg2rad(4.0 * lat))
                - 0.0023 * Math.cos(deg2rad(6.0 * lat));
    }
}
