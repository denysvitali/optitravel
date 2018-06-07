package ch.supsi.dti.i2b.shrug.optitravel.models;

import java.util.ArrayList;
import java.util.List;

public class ConnectionTrip extends Trip {
	private static final long serialVersionUID = -8968774798874265789L;
	private List<StopTrip> st_list = new ArrayList<>();
	public ConnectionTrip(StopTime first, StopTime second) {
		super();
		st_list.add(new ConnectionStopTrip(first, 0));
		st_list.add(new ConnectionStopTrip(second, 0));
	}

	@Override
	public List<StopTrip> getStopTrip() {
		return st_list;
	}

	@Override
	public Route getRoute() {
		return null;
	}

	@Override
	public void setStopTrip(List<StopTrip> stopTrip) {
		st_list = new ArrayList<>();
		st_list.addAll(stopTrip);
	}

	@Override
	public String getHeadSign() {
		StopTrip st_end = st_list.get(1);
		if(st_end == null || st_end.getStop() == null){
			return "Connection";
		}
		return "Connection to " + st_end.getStop().getName();
	}

	@Override
	public void setRoute(Route route) {

	}
}
