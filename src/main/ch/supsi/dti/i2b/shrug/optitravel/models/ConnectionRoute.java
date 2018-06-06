package ch.supsi.dti.i2b.shrug.optitravel.models;

public class ConnectionRoute extends Route {
	@Override
	public String getName() {
		return "Connection";
	}

	@Override
	public Operator getOperator() {
		return null;
	}

	@Override
	public String getUID() {
		return null;
	}
}
