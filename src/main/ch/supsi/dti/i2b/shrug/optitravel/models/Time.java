package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Route;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonIgnore;
import com.jsoniter.annotation.JsonWrapper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {
	private LocalTime localTime;

	public Time(String time){
		localTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
	}

	@Override
	public String toString() {
		return localTime.toString();
	}

	public int getHour(){
		return localTime.getHour();
	}

	public int getMinute(){
		return localTime.getMinute();
	}

	public int getSecond(){
		return localTime.getSecond();
	}
}
