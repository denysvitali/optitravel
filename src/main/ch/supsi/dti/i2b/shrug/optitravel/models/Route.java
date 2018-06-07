package ch.supsi.dti.i2b.shrug.optitravel.models;

import java.io.Serializable;

public abstract class Route implements Serializable {
	private static final long serialVersionUID = 7161914959743934806L;
    public abstract String getName();
    public abstract Operator getOperator();
    public abstract String getUID();
    public abstract RouteType getType();
    public abstract String getColor();
    public abstract String getTextColor();

	public abstract void setColor(String color);
}
