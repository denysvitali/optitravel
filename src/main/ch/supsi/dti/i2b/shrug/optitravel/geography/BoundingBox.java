package ch.supsi.dti.i2b.shrug.optitravel.geography;

public class BoundingBox {
    private Coordinate p1;
    private Coordinate p2;

    public BoundingBox(Coordinate p1, Coordinate p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    public Coordinate getP1() {
        return p1;
    }

    public Coordinate getP2() {
        return p2;
    }

    @Override
    public String toString() {
        return String.format("%f,%f,%f,%f",
                p1.getLat(),
                p1.getLng(),
                p2.getLat(),
                p2.getLng()
        );
    }

    // Expand the bouding box by adding a radius
    public BoundingBox expand(double meters){
        // Ported from GTFS Server:
        // https://github.com/denysvitali/gtfs-server/blob/master/src/routes/api/stops.rs

        // LAT = North to South (Y)
        // LNG = East to West   (X)

        Coordinate p1, p2;
        Coordinate c1, c2;

        double r1, r2;
        r1 = meters;
        r2 = meters;

        c1 = getP1();
        c2 = getP2();

        if (c1.getLng() < c2.getLng()) {
            // Blue
            p1 = new Coordinate(
                    c1.getLat() + r1 * 1.0 / Deg2Meter.lat(c1.getLat()),
                    c1.getLng() - r1 * 1.0 / Deg2Meter.lng(c1.getLat())
            );

            p2 = new Coordinate(
                    c2.getLat() + r2 * 1.0 / Deg2Meter.lat(c2.getLat()),
                    c2.getLng() + r2 * 1.0 / Deg2Meter.lng(c2.getLat())
            );
        } else if(c1.getLng() > c2.getLng()) {
            // Orange
            p1 = new Coordinate(
                    c2.getLat() + r2 * 1.0 / Deg2Meter.lat(c2.getLat()),
                    c2.getLng() - r2 * 1.0 / Deg2Meter.lng(c2.getLat())
            );

            p2 = new Coordinate(
                    c1.getLat() - r1 * 1.0 / Deg2Meter.lat(c1.getLat()),
                    c1.getLng() + r1 * 1.0 / Deg2Meter.lng(c1.getLat())
            );
        } else {
            if(c1.getLng() > c2.getLng()){
                // Orange
                p1 = new Coordinate(
                        c2.getLat() + r2 * 1.0 / Deg2Meter.lat(c2.getLat()),
                        c2.getLng() - r2 * 1.0 / Deg2Meter.lng(c2.getLat())
                );

                p2 = new Coordinate(
                        c1.getLat() - r1 * 1.0 / Deg2Meter.lat(c1.getLat()),
                        c1.getLng() + r1 * 1.0 / Deg2Meter.lng(c1.getLat())
                );
            } else {
                // Blue
                p1 = new Coordinate(
                        c1.getLat() + r1 * 1.0 / Deg2Meter.lat(c1.getLat()),
                        c1.getLng() - r1 * 1.0 / Deg2Meter.lng(c1.getLat())
                );

                p2 = new Coordinate(
                        c2.getLat() + r2 * 1.0 / Deg2Meter.lat(c2.getLat()),
                        c2.getLng() + r2 * 1.0 / Deg2Meter.lng(c2.getLat())
                );
            }
        }

        return new BoundingBox(p1,p2);
    }
}
