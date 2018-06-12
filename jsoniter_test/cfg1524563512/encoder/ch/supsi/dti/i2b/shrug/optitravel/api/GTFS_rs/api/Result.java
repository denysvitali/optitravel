package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api;
public class Result implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"result\":", 10);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Result)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Result obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj.getResult() == null) { stream.writeNull(); } else {
stream.write((byte)'{', (byte)'}');
jsoniter_codegen.cfg1524563512.encoder.com.jsoniter.any.Any.encode_((com.jsoniter.any.Any)obj.getResult(), stream);

}
stream.writeRaw(",\"meta\":", 8);
if (obj.getMeta() == null) { stream.writeNull(); } else {
stream.writeRaw("{\"success\":", 11);
jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta.encode_((ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta)obj.getMeta(), stream);
stream.write((byte)'}');
}
}
}
