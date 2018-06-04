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

        double c1_x = c1.getLng() * Deg2Meter.lng(c1.getLat());
        double c1_y = c1.getLat() * Deg2Meter.lat(c1.getLat());

        double c2_x = c2.getLng() * Deg2Meter.lng(c2.getLat());
        double c2_y = c2.getLat() * Deg2Meter.lat(c2.getLat());

        // Versor computation
		/*double[] ver = new double[]{c2_x - c1_x, c2_y - c1_y};
		double distance = Distance.distance(c1,c2);
		ver[0] *= 1/distance;
		ver[1] *= 1/distance;

		double c1f_x = c1_x - (distance + meters) * ver[0];
		double c1f_y = c1_y - (distance + meters) * ver[1];

		double c2f_x = c2_x + (distance + meters) * ver[0];
		double c2f_y = c2_y + (distance + meters) * ver[1];

		double c1f_lng = c1f_x / Deg2Meter.lng(c1.getLat());
		double c1f_lat = c1f_y / Deg2Meter.lat(c1.getLat());

		double c2f_lng = c2f_x / Deg2Meter.lng(c2.getLat());
		double c2f_lat = c2f_y / Deg2Meter.lat(c2.getLat());*/

		// Get minimum coordinate as c1,
		// get maximum coordinate as c2
		double c1f_x,c1f_y,c2f_x,c2f_y;

		if(c1_x < c2_x){
			if(c1_y < c2_y){
				/*
						c2
					c1
				 */
				c1f_x = c1_x - meters;
				c1f_y = c1_y - meters;

				c2f_x = c2_x + meters;
				c2f_y = c2_y + meters;
			} else {
				/*
					c2
						c1
				 */
				c1f_x = c1_x + meters;
				c1f_y = c1_y + meters;

				c2f_x = c2_x - meters;
				c2f_y = c2_y - meters;
			}
		} else {
			if(c1_y < c2_y){
				/*
					c2
						c1
				 */
				c1f_x = c1_x + meters;
				c1f_y = c1_y + meters;

				c2f_x = c2_x - meters;
				c2f_y = c2_y - meters;
			} else {
				/*
						c1
					c2
				 */
				c1f_x = c1_x - meters;
				c1f_y = c1_y - meters;

				c2f_x = c2_x + meters;
				c2f_y = c2_y + meters;
			}
		}

		double c1f_lng = c1f_x / Deg2Meter.lng(c1.getLat());
		double c1f_lat = c1f_y / Deg2Meter.lat(c1.getLat());

		double c2f_lng = c2f_x / Deg2Meter.lng(c2.getLat());
		double c2f_lat = c2f_y / Deg2Meter.lat(c2.getLat());

		p1 = new Coordinate(c1f_lat, c1f_lng);
		p2 = new Coordinate(c2f_lat, c2f_lng);


        return new BoundingBox(p1,p2);
    }

    public String toPostGIS(){
    	return String.format("ST_MakeEnvelope(\n" +
				"%f,%f,%f,%f, 4326\n" +
				")", p1.getLng(), p1.getLat(), p2.getLng(), p2.getLat());
	}
}
