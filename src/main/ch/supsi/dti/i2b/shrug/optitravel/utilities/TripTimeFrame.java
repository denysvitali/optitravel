package ch.supsi.dti.i2b.shrug.optitravel.utilities;

public enum TripTimeFrame {
    LEAVE_NOW("Leave now"), DEPART_AT("Depart at"), ARRIVE_BY("Arrive by");
    String str;

    TripTimeFrame(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
