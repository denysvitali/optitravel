package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.parser;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results.ScheduleStopPairResult;
import com.jsoniter.JsonIterator;

import java.util.List;

public class ScheduleStopPairResultParser {
	public static ScheduleStopPairResult parse(String json){
		return JsonIterator.deserialize(json, ScheduleStopPairResult.class);
	}
}
