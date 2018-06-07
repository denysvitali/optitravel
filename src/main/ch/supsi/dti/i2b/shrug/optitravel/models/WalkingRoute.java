package ch.supsi.dti.i2b.shrug.optitravel.models;

import java.util.Objects;

public class WalkingRoute extends Route {
	@Override
	public String getName() {
		return "Walking";
	}

	@Override
	public Operator getOperator() {
		return new WalkingOperator();
	}

	@Override
	public String getUID() {
		return "wr";
	}

	@Override
	public RouteType getType() {
		return null;
	}

	@Override
	public String getColor() {
		return "#2196F3";
	}

	@Override
	public String getTextColor() {
		return null;
	}

	@Override
	public void setColor(String color) {

	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof WalkingRoute){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName());
	}

	@Override
	public String toString() {
		return getName();
	}
}
