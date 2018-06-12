package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;
public class TripTimeStop implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"next_stop\":", 13);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj.next_stop == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.next_stop);
}
stream.writeRaw(",\"time\":", 8);
if (obj.getTime() == null) { stream.writeNull(); } else {
stream.writeRaw("{\"hour\":", 8);
jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.models.Time.encode_((ch.supsi.dti.i2b.shrug.optitravel.models.Time)obj.getTime(), stream);
stream.write((byte)'}');
}
stream.writeRaw(",\"trip\":", 8);
if (obj.getTrip() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getTrip());
}
stream.writeRaw(",\"nextStop\":", 12);
if (obj.getNextStop() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getNextStop());
}
}
}
