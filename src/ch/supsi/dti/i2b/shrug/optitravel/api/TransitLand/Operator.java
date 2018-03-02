package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

public class Operator {
    private String operator_onestop_id;
    private String operator_name;

    public Operator(){

    }

    public String getName() {
        return operator_name;
    }

    public String getId() {
        return operator_onestop_id;
    }
}
