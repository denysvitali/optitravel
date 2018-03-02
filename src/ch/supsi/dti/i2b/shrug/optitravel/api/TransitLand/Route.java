package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

public class Route {
    private String route_onestop_id;
    private String name;
    private Operator operator;

    public Route(){

    }

    public String getName() {
        return name;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getId() {
        return route_onestop_id;
    }
}
