package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api;

import com.jsoniter.any.Any;

import java.util.Arrays;

public class Result {
    public Any result;
    public Meta meta;

	public Any getResult() {
		return result;
	}

	public Meta getMeta() {
		return meta;
	}
}
