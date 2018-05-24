package ch.supsi.dti.i2b.shrug.optitravel.models;

public class WalkingRoute extends Route {
	@Override
	public String getName() {
		return "Walking";
	}

	@Override
	public Operator getOperator() {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof WalkingRoute){
			return true;
		}
		return false;
	}
}
