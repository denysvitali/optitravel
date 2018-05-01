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
}
