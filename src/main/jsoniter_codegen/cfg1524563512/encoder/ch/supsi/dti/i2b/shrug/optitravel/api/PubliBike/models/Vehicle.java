package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;
public class Vehicle implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"name\":", 8);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Vehicle)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Vehicle obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj.getName() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getName());
}
stream.writeRaw(",\"id\":", 6);
stream.writeVal((int)obj.getId());
stream.writeRaw(",\"type\":", 8);
if (obj.getType() == null) { stream.writeNull(); } else {
stream.writeRaw("{\"name\":", 8);
jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.VehicleType.encode_((ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.VehicleType)obj.getType(), stream);
stream.write((byte)'}');
}
}
}
