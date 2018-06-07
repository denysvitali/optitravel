package ch.supsi.dti.i2b.shrug.optitravel.models;

import java.io.Serializable;

public abstract class Route implements Serializable {
    public abstract String getName();
    public abstract Operator getOperator();
    public abstract String getUID();
    public abstract RouteType getType();
    public abstract String getColor();
    public abstract String getTextColor();
}
