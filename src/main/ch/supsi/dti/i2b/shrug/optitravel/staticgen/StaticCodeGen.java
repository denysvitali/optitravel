package ch.supsi.dti.i2b.shrug.optitravel.staticgen;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.*;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.models.RouteType;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.EncodingMode;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.DecodingMode;
import com.jsoniter.spi.TypeLiteral;
import com.jsoniter.static_codegen.StaticCodegenConfig;

public class StaticCodeGen implements StaticCodegenConfig {

	@Override
	public void setup() {
		// register custom decoder or extensions before codegen
		// so that we doing codegen, we know in which case, we need to callback
		JsonIterator.setMode(DecodingMode.STATIC_MODE);
		JsonStream.setMode(EncodingMode.STATIC_MODE);
		JsonStream.setIndentionStep(2);
	}


	@Override
	public TypeLiteral[] whatToCodegen() {
		return new TypeLiteral[]{
				TypeLiteral.create(Agency.class),
				TypeLiteral.create(PaginatedList.class),
				TypeLiteral.create(Route.class),
				TypeLiteral.create(RouteType.class),
				TypeLiteral.create(Stop.class),
				TypeLiteral.create(StopTimes.class),
				TypeLiteral.create(Trip.class),
				TypeLiteral.create(TripTimeStop.class),
				TypeLiteral.create(Result.class),
				TypeLiteral.create(ResultArray.class),
				TypeLiteral.create(Meta.class),
				TypeLiteral.create(Pagination.class),
				TypeLiteral.create(Error.class),
				TypeLiteral.create(StopTrip.class)
		};
	}
}