package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api;
public class Error implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"code\":", 8);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
stream.writeVal((int)obj.code);
stream.writeRaw(",\"message\":", 11);
if (obj.message == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.message);
}
}
}
