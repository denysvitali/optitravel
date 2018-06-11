package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;
public class StopTimes implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"time\":", 8);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTimes)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTimes obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj.getTime() == null) { stream.writeNull(); } else {
stream.write((byte)'[');
jsoniter_codegen.cfg1524563512.encoder.java.util.List_ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop.encode_((java.util.List)obj.getTime(), stream);
stream.write((byte)']');
}
stream.writeRaw(",\"stop\":", 8);
if (obj.getStop() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getStop());
}
}
}
