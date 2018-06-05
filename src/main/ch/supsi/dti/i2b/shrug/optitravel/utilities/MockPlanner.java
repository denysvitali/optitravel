package ch.supsi.dti.i2b.shrug.optitravel.utilities;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Result;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.Plan;
import ch.supsi.dti.i2b.shrug.optitravel.planner.Planner;
import com.jsoniter.JsonIterator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockPlanner extends Planner {

    private Coordinate from, to;

    public MockPlanner(Coordinate from, Coordinate to) {
        super(from, to);
        this.from = from;
        this.to = to;
    }

    @Override
    public List<Plan> getPlans() {
        File f = new File(getClass().getClassLoader().getResource("json/gtfs/t-fb6573-3ta20422j1811r.json").getFile());
        StringBuilder builder = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        in.lines().forEach(builder::append);
        String json = builder.toString();

        List<Plan> plans = new ArrayList<>();
        List<Trip> trips = null;

        Result a = JsonIterator.deserialize(json, Result.class);
        trips.add(a.getResult().as(Trip.class));
        List<ch.supsi.dti.i2b.shrug.optitravel.models.Trip> tripss = new ArrayList<>(trips);
        Plan p = new Plan(tripss, null, null, from, to);
        return plans;
    }
}
