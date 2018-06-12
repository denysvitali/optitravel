package ch.supsi.dti.i2b.shrug.optitravel.codegen;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.StopDistance;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Result;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.models.Route;
import ch.supsi.dti.i2b.shrug.optitravel.models.StopTime;
import com.jsoniter.JsonIterator;
import com.jsoniter.spi.DecodingMode;
import com.jsoniter.spi.JsoniterSpi;
import com.jsoniter.spi.TypeLiteral;
import com.jsoniter.static_codegen.StaticCodegenConfig;
import javassist.compiler.CodeGen;

import java.util.List;
import java.util.Map;

public class CGConfig implements StaticCodegenConfig {

	@Override
	public void setup() {
		// register custom decoder or extensions before codegen
		// so that we doing codegen, we know in which case, we need to callback
		JsonIterator.setMode(DecodingMode.STATIC_MODE); // must set to static mode
	}

	@Override
	public TypeLiteral[] whatToCodegen() {
		return new TypeLiteral[]{
				// generic types, need to use this syntax
				new TypeLiteral<List<Integer>>() {
				},
				new TypeLiteral<Map<String, Object>>() {
				},
				// array
				TypeLiteral.create(int[].class),
				// object
				//TypeLiteral.create(StopTime.class),
				//TypeLiteral.create(Agency.class),
				//TypeLiteral.create(Route.class),
				//TypeLiteral.create(Stop.class),
				TypeLiteral.create(StopTimes.class),
				//TypeLiteral.create(Trip.class),
				TypeLiteral.create(TripTimeStop.class),
				TypeLiteral.create(Result.class),
				TypeLiteral.create(ResultArray.class),
				TypeLiteral.create(StopDistance.class),
				TypeLiteral.create(PickUp.class),
				TypeLiteral.create(DropOff.class),
				// PubliBike
				TypeLiteral.create(VehicleType.class),
				TypeLiteral.create(Vehicle.class),
				TypeLiteral.create(Network.class),
				TypeLiteral.create(Sponsor.class),
				TypeLiteral.create(Station.class),
				TypeLiteral.create(StationState.class),
				TypeLiteral.create(Tariff.class),
				TypeLiteral.create(TariffModel.class),
		};
	}
}
