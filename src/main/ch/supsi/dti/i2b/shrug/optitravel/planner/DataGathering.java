package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.PubliBikeWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIWrapper;

public class DataGathering{
    private TransitLandAPIWrapper wTL = new TransitLandAPIWrapper();
    private GTFSrsWrapper wGTFS = new GTFSrsWrapper();
    private PubliBikeWrapper wPB = new PubliBikeWrapper();

    DataGathering(){

    }

    public GTFSrsWrapper getwGTFS() {
        return wGTFS;
    }

    public PubliBikeWrapper getwPB() {
        return wPB;
    }

    public TransitLandAPIWrapper getwTL() {
        return wTL;
    }
}