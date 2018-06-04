package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.sort.TripSort;

import java.util.List;

public class TripSearch {
	public List<String> stops_visited;
	public String route;
	public String departure_after;
	public String arrival_before;
	public Integer offset;
	public Integer per_page;
	public TripSort sort_by;
	public AscDesc sort_order;
}
