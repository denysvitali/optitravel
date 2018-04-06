package ch.supsi.dti.i2b.shrug.optitravel.geography;

public class Coordinate {
    private double lat;
    private double lng;

    public Coordinate(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }

        if(obj.getClass() != this.getClass()){
            return false;
        }

        Coordinate a;
        try {
            a = (Coordinate) obj;
        } catch(ClassCastException ex){
            return false;
        }

        if((float) a.getLat() != (float) this.getLat()){
            return false;
        }

        if((float) a.getLng() != (float) this.getLng()){
            return false;
        }

        return true;
    }

    @Override
    public String toString(){
        return String.format("%f,%f", lat, lng);
    }
}
