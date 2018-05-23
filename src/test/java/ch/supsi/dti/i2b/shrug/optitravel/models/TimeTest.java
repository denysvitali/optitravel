package ch.supsi.dti.i2b.shrug.optitravel.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeTest {
	@Test
	public void testAfter(){
		Time t = new Time("12:00:00");
		Time t2 = new Time("12:05:01");

		assertTrue(t2.isAfter(t));
		assertFalse(t.isAfter(t2));

		assertTrue(Time.isAfter(t2, t));
		assertFalse(Time.isAfter(t, t2));
	}

	@Test
	public void testDiff(){
		Time t = new Time("12:00:00");
		Time t2 = new Time("12:54:00");

		assertEquals(54.0, Time.diffMinutes(t2, t));

		t = new Time("12:00:00");
		t2 = new Time("23:59:00");

		assertEquals(11.0 * 60.0 + 59.0, Time.diffMinutes(t2, t));
	}
}
