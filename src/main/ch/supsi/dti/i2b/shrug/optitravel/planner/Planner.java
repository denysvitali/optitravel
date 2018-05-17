package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.GPSCoordinates;
import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.Plan;
import ch.supsi.dti.i2b.shrug.optitravel.models.Stop;

import java.util.ArrayList;
import java.util.List;

public class Planner {
    private Coordinate from;
    private Coordinate to;
    private List<Plan> plans = new ArrayList<>();


    Planner(Coordinate from, Coordinate to){
        this.from = from;
        this.to = to;
    }

    List<Plan> getPlans(){
        if(plans.size() == 0){
            computePlans();
        }
        return plans;
    }

    public void computePlans() {
        DataGathering dg = new DataGathering();

        List<Stop> stops = new ArrayList<>();
        BoundingBox boundingBox = new BoundingBox(from, to);
        boundingBox = boundingBox.expand(1500); // Expand the BB by 1500 meters

        try {
            List<ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Stop>
                    tl_stops = dg.getwTL().getStopsByBBox(boundingBox);
            stops.addAll(tl_stops);
        } catch (TransitLandAPIError transitLandAPIError) {
            transitLandAPIError.printStackTrace();
        }

    }
}
