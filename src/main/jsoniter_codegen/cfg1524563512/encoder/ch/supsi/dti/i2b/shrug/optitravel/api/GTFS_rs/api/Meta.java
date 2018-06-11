package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api;
public class Meta implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"success\":", 11);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
stream.writeVal((boolean)obj.success);
stream.writeRaw(",\"error\":", 9);
if (obj.error == null) { stream.writeNull(); } else {
stream.writeRaw("{\"code\":", 8);
jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error.encode_((ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error)obj.error, stream);
stream.write((byte)'}');
}
stream.writeRaw(",\"pagination\":", 14);
if (obj.pagination == null) { stream.writeNull(); } else {
stream.writeRaw("{\"offset\":", 10);
jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination.encode_((ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination)obj.pagination, stream);
stream.write((byte)'}');
}
}
}
