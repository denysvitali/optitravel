package ch.supsi.dti.i2b.shrug.optitravel.models;

import java.io.Serializable;

public abstract class Operator implements Serializable {
    public abstract String getName();
    public abstract String getURL();
}
