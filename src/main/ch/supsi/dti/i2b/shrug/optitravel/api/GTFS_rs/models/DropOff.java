package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

public enum DropOff {
    RegularlyScheduled(0),
    NotAvailable(1),
    MustArrangeWithAgency(2),
    MustCoordinateWithAgency(3);

    DropOff(int i){

    }
}
