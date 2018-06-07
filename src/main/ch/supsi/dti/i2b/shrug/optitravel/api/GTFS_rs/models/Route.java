package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.models.Operator;
import ch.supsi.dti.i2b.shrug.optitravel.models.RouteType;

import java.util.Objects;

public class Route extends ch.supsi.dti.i2b.shrug.optitravel.models.Route {
	private static final long serialVersionUID = 6099999255747497502L;
    public String uid;
    public String agency_id;
    public String short_name;
    public String long_name;
    public String description;
    public String color;
    public String text_color;
    public int type;

    public Route(){}
    public Route(String uid){
        this.uid = uid;
    }

    public String getUID() {
        return uid;
    }

	@Override
	public RouteType getType() {
		return RouteType.getRoute(type);
	}

	@Override
	public String getColor() {
		return color;
	}

	public String getTextColor() {
		return text_color;
	}

	@Override
	public void setColor(String color) {
		this.color = color;
	}

	public String getAgencyUID() {
        return agency_id;
    }

    public String getShortName() {
        return short_name;
    }

    public String getLongName() {
        return long_name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return getShortName();
    }

    @Override
    public Operator getOperator() {
        return new Agency(getAgencyUID());
    }

	@Override
    public String toString() {
        if(getShortName() != null){
            return String.format("%s (%s)", getShortName(), getUID());
        }
        return String.format("%s", getUID());
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Route route = (Route) o;
		return type == route.type &&
				Objects.equals(uid, route.uid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uid);
	}
}
