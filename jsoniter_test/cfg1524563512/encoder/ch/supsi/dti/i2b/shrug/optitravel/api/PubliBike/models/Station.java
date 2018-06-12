package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;
public class Station implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"name\":", 8);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Station)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Station obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj.getName() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getName());
}
stream.writeRaw(",\"id\":", 6);
stream.writeVal((int)obj.getId());
stream.writeRaw(",\"state\":", 9);
if (obj.getState() == null) { stream.writeNull(); } else {
stream.writeRaw("{\"name\":", 8);
jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.StationState.encode_((ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.StationState)obj.getState(), stream);
stream.write((byte)'}');
}
stream.writeRaw(",\"address\":", 11);
if (obj.getAddress() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getAddress());
}
stream.writeRaw(",\"coordinate\":", 14);
if (obj.getCoordinate() == null) { stream.writeNull(); } else {
stream.writeRaw("{\"lat\":", 7);
jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate.encode_((ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate)obj.getCoordinate(), stream);
stream.write((byte)'}');
}
stream.writeRaw(",\"sponsors\":", 12);
if (obj.getSponsors() == null) { stream.writeNull(); } else {
stream.write((byte)'[');
jsoniter_codegen.cfg1524563512.encoder.java.util.ArrayList_ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Sponsor.encode_((java.util.ArrayList)obj.getSponsors(), stream);
stream.write((byte)']');
}
stream.writeRaw(",\"vehicles\":", 12);
if (obj.getVehicles() == null) { stream.writeNull(); } else {
stream.write((byte)'[');
jsoniter_codegen.cfg1524563512.encoder.java.util.ArrayList_com.jsoniter.any.Any.encode_((java.util.ArrayList)obj.getVehicles(), stream);
stream.write((byte)']');
}
stream.writeRaw(",\"zip\":", 7);
if (obj.getZip() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getZip());
}
stream.writeRaw(",\"network\":", 11);
if (obj.getNetwork() == null) { stream.writeNull(); } else {
stream.writeRaw("{\"name\":", 8);
jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Network.encode_((ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Network)obj.getNetwork(), stream);
stream.write((byte)'}');
}
stream.writeRaw(",\"city\":", 8);
if (obj.getCity() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getCity());
}
}
}
