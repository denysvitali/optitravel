package ch.supsi.dti.i2b.shrug.optitravel.geography;

import org.junit.jupiter.api.Test;

import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.BELLINZONA_COORDINATE;
import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.LUGANO_COORDINATE;

public class BBoxTest {
	@Test
	public void bboxTest1(){
		BoundingBox bbox1 = new BoundingBox(LUGANO_COORDINATE, BELLINZONA_COORDINATE);
		System.out.println(bbox1.expand(5000).toPostGIS());
	}
}
