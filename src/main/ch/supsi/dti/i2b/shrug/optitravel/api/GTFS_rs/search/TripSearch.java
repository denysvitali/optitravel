package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.sort.TripSort;
import ch.supsi.dti.i2b.shrug.optitravel.models.Time;

import java.util.List;

public class TripSearch {
	public List<String> stops_visited;
	public String route;
	public Time departure_after;
	public Time arrival_before;
	public Integer offset;
	public Integer per_page;
	public TripSort sort_by;
	public AscDesc sort_order;
}
