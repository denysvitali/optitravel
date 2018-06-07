package ch.supsi.dti.i2b.shrug.optitravel.models;

public class WaitingRoute extends Route {
	@Override
	public String getName() {
		return "Waiting";
	}

	@Override
	public Operator getOperator() {
		return null;
	}

	@Override
	public String getUID() {
		return "WaitingRoute";
	}

	@Override
	public RouteType getType() {
		return null;
	}

	@Override
	public String getColor() {
		return null;
	}

	@Override
	public String getTextColor() {
		return null;
	}

	@Override
	public String toString() {
		return getName();
	}
}
