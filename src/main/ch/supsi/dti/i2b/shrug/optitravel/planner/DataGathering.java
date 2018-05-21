package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsError;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.PubliBikeWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.models.Route;
import ch.supsi.dti.i2b.shrug.optitravel.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.models.Trip;

import java.util.ArrayList;
import java.util.List;

public class DataGathering{
    private TransitLandAPIWrapper wTL = new TransitLandAPIWrapper();
    private GTFSrsWrapper wGTFS = new GTFSrsWrapper();
    private PubliBikeWrapper wPB = new PubliBikeWrapper();

    private List<Trip> trips = new ArrayList<>();
    private List<Stop> stops = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();

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

    public List<Stop> getStops(BoundingBox boundingBox){
    	if(stops.size() != 0){
    		return stops;
		}
    	try{
			stops.addAll(getwGTFS().getStopsByBBox(boundingBox));
			stops.addAll(getwTL().getStopsByBBox(boundingBox));
		} catch(GTFSrsError | TransitLandAPIError err){
			err.printStackTrace();
		}

		return stops;
	}

	public List<Trip> getTrips(BoundingBox boundingBox) {
		if(trips.size() != 0){
			return trips;
		}
		try{
			trips.addAll(getwGTFS().getTripsByBBox(boundingBox));
			trips.addAll(getwTL().getTripsByBBox(boundingBox));
		} catch(GTFSrsError | TransitLandAPIError err){
			err.printStackTrace();
		}

		return trips;
	}
}