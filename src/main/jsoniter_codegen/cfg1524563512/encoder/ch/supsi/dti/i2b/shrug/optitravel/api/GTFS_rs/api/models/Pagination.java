package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models;
public class Pagination implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"offset\":", 10);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
stream.writeVal((int)obj.offset);
stream.writeRaw(",\"limit\":", 9);
stream.writeVal((int)obj.limit);
}
}
