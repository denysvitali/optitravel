package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.ScheduleStopPair;

import java.util.ArrayList;

public class ScheduleStopPairResult {
    private ArrayList<ScheduleStopPair> schedule_stop_pairs;
    private Meta meta;

    public ArrayList<ScheduleStopPair> getScheduleStopPairs() {

        return schedule_stop_pairs;
    }
    public Meta getMeta() {

        return meta;
    }
}
