package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;
public class Stop implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"name\":", 8);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj.getName() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getName());
}
stream.writeRaw(",\"type\":", 8);
stream.writeVal((int)obj.getType());
stream.writeRaw(",\"coordinate\":", 14);
if (obj.getCoordinate() == null) { stream.writeNull(); } else {
stream.writeRaw("{\"lat\":", 7);
jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate.encode_((ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate)obj.getCoordinate(), stream);
stream.write((byte)'}');
}
stream.writeRaw(",\"lat\":", 7);
stream.writeVal((double)obj.getLat());
stream.writeRaw(",\"lng\":", 7);
stream.writeVal((double)obj.getLng());
stream.writeRaw(",\"uid\":", 7);
if (obj.getUid() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getUid());
}
stream.writeRaw(",\"parentStop\":", 14);
if (obj.getParentStop() == null) { stream.writeNull(); } else {
stream.writeRaw("{\"name\":", 8);
jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.models.Stop.encode_((ch.supsi.dti.i2b.shrug.optitravel.models.Stop)obj.getParentStop(), stream);
stream.write((byte)'}');
}
}
}
