package ch.supsi.dti.i2b.shrug.optitravel.models;

import java.util.List;

public abstract class Trip {
	public abstract List<StopTrip> getStopTrip();
	public abstract Route getRoute();

	public int getStopIndex(Stop s){
		List<StopTrip> stopTrips = getStopTrip();
		if(stopTrips == null){
			return -1;
		}

		for(StopTrip st : stopTrips){
			if(st.getStop().equals(s)){
				return stopTrips.indexOf(st);
			}
		}

		return -1;
	}
}
