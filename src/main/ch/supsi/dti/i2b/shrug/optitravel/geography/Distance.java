package ch.supsi.dti.i2b.shrug.optitravel.geography;

public class Distance {
    private static double EARTH_RADIUS = 6371E3; // Earth Radius, in meters
    public Distance(){

    }

    public static double distance(Coordinate c1, Coordinate c2){
        // Reference: https://www.movable-type.co.uk/scripts/latlong.html
        double phi1 = Math.toRadians(c1.getLat());
        double phi2 = Math.toRadians(c2.getLat());

        double dphi = Math.toRadians(c2.getLat() - c1.getLat());
        double dlam = Math.toRadians(c2.getLng() - c1.getLng());

        double a =  Math.sin(dphi/2) * Math.sin(dphi/2) +
                    Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlam/2) * Math.sin(dlam/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return EARTH_RADIUS * c;
    }
}
