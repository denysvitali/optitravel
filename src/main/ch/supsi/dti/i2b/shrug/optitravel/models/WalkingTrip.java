package ch.supsi.dti.i2b.shrug.optitravel.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WalkingTrip extends Trip {

	private StopTime from;
	private StopTime to;

	public WalkingTrip(StopTime f, StopTime t){
		from = f;
		to = t;
	}

	@Override
	public List<StopTrip> getStopTrip() {
		List<StopTrip> stl = new ArrayList<>();
		stl.add(new WalkingStopTrip(
				from.getStop(),
				from.getTime().toString(),
				from.getTime().toString(),
				0,
				DropOff.NotAvailable,
				PickUp.NotAvailable
		));

		stl.add(new WalkingStopTrip(
				to.getStop(),
				to.getTime().toString(),
				to.getTime().toString(),
				0,
				DropOff.NotAvailable,
				PickUp.NotAvailable
		));

		return stl;
	}

	@Override
	public Route getRoute() {
		return new WalkingRoute();
	}

	@Override
	public void setStopTrip(List<StopTrip> stopTrip) {
	}

	@Override
	public String getHeadSign() {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof WalkingTrip)){
			return false;
		}

		WalkingTrip wt = (WalkingTrip) obj;
		return from.hashCode() == wt.from.hashCode() &&
				to.hashCode() == wt.to.hashCode();
	}

	@Override
	public String toString() {
		return String.format("Walking %s (@%s) - %s (@%s)", from.getStop(), from.getTime(), to.getStop(), to.getTime());
	}

	@Override
	public int hashCode() {
		return Objects.hash(from.getCoordinate(), to.getCoordinate(), WalkingRoute.class);
	}
}
