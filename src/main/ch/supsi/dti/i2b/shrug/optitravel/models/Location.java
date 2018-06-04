package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;

public abstract class Location {
	private Coordinate coordinate;

	public Location(){

	}

	public Location(Coordinate c){
		coordinate = c;
	}
    public abstract Coordinate getCoordinate();

    @Override
    public int hashCode() {
        return getCoordinate().hashCode();
    }

	@Override
	public boolean equals(Object obj) {
    	if(this == obj){
    		return true;
		}

		if(obj instanceof Location){
			Location l = (Location) obj;
			return this.getCoordinate().equals(l.getCoordinate());
		}
		return false;
	}
}
