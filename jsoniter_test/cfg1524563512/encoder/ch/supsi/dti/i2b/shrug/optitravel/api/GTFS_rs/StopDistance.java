package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs;
public class StopDistance implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"stop\":", 8);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.StopDistance)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.StopDistance obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj.getStop() == null) { stream.writeNull(); } else {
stream.writeRaw("{\"name\":", 8);
jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop.encode_((ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop)obj.getStop(), stream);
stream.write((byte)'}');
}
stream.writeRaw(",\"distance\":", 12);
stream.writeVal((double)obj.getDistance());
}
}
