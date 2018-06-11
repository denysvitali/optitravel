package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta;
import com.jsoniter.any.Any;

import java.util.ArrayList;
import java.util.List;

public class PaginatedList<T> {
	public List<T> result;
	public Meta meta;

	public PaginatedList(List<T> result, Meta meta) {
		this.result = result;
		this.meta = meta;
	}

	public PaginatedList() {

	}

	public Meta getMeta() {
		return meta;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public void setMeta(Meta metaobj) {
		this.meta = metaobj;
	}
}
