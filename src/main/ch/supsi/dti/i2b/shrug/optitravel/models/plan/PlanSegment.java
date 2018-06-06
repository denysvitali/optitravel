package ch.supsi.dti.i2b.shrug.optitravel.models.plan;

import ch.supsi.dti.i2b.shrug.optitravel.models.*;

import java.util.ArrayList;
import java.util.List;

public class PlanSegment {
	private Trip trip;
	private TimedLocation start;
	private TimedLocation end;
	private List<TimedLocation> elements = new ArrayList<>();

	public PlanSegment(Trip t, TimedLocation s, TimedLocation e){
		trip = t;
		start = s;
		end = e;
		elements.add(s);
		elements.add(e);
	}

	public PlanSegment(Trip t, TimedLocation s){
		trip = t;
		start = s;
		elements.add(s);
	}

	public Trip getTrip() {
		return trip;
	}

	public Route getRoute() {
		if(trip == null){
			return null;
		}
		return trip.getRoute();
	}

	public String getName() {
		Route r = getRoute();
		if(r == null){
			return null;
		}
		return r.getName();
	}

	public TimedLocation getStart() {
		return start;
	}

	public TimedLocation getEnd() {
		return end;
	}

	public void setEnd(TimedLocation l) {
		end = l;
	}

	public void addElement(TimedLocation tl) {
		elements.add(tl);
	}

	public List<TimedLocation> getElements() {
		return elements;
	}


}
