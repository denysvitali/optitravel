package ch.supsi.dti.i2b.shrug.optitravel.models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {
	private LocalTime localTime;

	public Time(){

	}

	public Time(String time){
		localTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
	}

	public Time(LocalTime time){
		localTime = time;
	}

	public static double diffMinutes(Time time1, Time time2) {
		int hours = time1.getHour() - time2.getHour();
		int minutes = time1.getMinute() - time2.getMinute();
		int seconds = time1.getSecond() - time2.getSecond();

		return hours*60 + minutes + seconds/60;
	}

	@Override
	public String toString() {
		return localTime.format(DateTimeFormatter.ISO_TIME);
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Time)){
			return false;
		}

		return localTime.equals(((Time) obj).localTime);
	}

	public boolean isAfter(Time t){
		return Time.isAfter(this, t);
	}

	public static int compareTo(Time t1, Time t2){
		return t1.localTime.compareTo(t2.localTime);
	}

	// t1 is after t2?
	public static boolean isAfter(Time t1, Time t2){
		if(t1.getHour() < t2.getHour()){
			return false;
		}

		if(t1.getHour() > t2.getHour()){
			return true;
		}

		if(t1.getMinute() < t2.getMinute()){
			return false;
		}

		if(t1.getMinute() > t2.getMinute()){
			return true;
		}

		return t1.getSecond() >= t2.getSecond();

	}

	public static Time addMinutes(Time t1, int minutes){
		return new Time(t1.localTime.plusMinutes(minutes));
	}

	@Override
	public int hashCode() {
		return localTime.hashCode();
	}


}
