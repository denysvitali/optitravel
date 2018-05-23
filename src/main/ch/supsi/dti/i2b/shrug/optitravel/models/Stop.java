package ch.supsi.dti.i2b.shrug.optitravel.models;

public abstract class Stop extends Location {
    public abstract String getName();

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
