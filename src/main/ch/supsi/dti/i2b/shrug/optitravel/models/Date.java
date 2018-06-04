package ch.supsi.dti.i2b.shrug.optitravel.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
	private LocalDate date;

	public Date(String date){
		this.date = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
	}

	public Date(LocalDate ld){
		this.date = ld;
	}

	@Override
	public String toString() {
		return this.date.format(DateTimeFormatter.ISO_DATE);
	}
}
