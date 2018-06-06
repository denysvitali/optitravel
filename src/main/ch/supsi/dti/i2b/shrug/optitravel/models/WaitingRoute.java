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
	public String toString() {
		return getName();
	}
}
