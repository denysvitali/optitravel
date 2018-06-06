package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.GPSCoordinates;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.plan.PlanSegment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Plan {
    private TimedLocation start_location;
    private TimedLocation end_location;

    private List<PlanSegment> planSegments = new ArrayList<>();

    public Plan(){}

	public void setPlanSegments(List<PlanSegment> planSegments) {
		this.planSegments = planSegments;
	}

	public List<PlanSegment> getPlanSegments() {
		return planSegments;
	}

	public Plan(List<TimedLocation> timedLocationList) {
    	Trip t = null;
    	TimedLocation st_start = null;
    	TimedLocation st_end = null;

		PlanSegment ps = null;

    	for(TimedLocation tl : timedLocationList){
			if(st_start == null){
				st_start = tl;
				continue;
			}

			if(ps == null){
				ps = new PlanSegment(tl.getTrip(), tl);
				continue;
			}

			ps.addElement(tl);
			if(!ps.getTrip().equals(tl.getTrip())){
				ps.setEnd(tl);
				planSegments.add(ps);
				ps = new PlanSegment(tl.getTrip(), tl);
			}

			ps.setEnd(tl);
			st_end = tl;
		}
		start_location = st_start;
		end_location = st_end;
	}

	public Location getEndLocation() {
		return end_location;
	}

	public Location getStartLocation() {
		return start_location;
	}

}
