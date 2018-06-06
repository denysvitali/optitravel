package ch.supsi.dti.i2b.shrug.optitravel.models;

import java.io.Serializable;
import java.util.List;

public abstract class Trip implements Serializable {
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

	public abstract void setStopTrip(List<StopTrip> stopTrip);
}
