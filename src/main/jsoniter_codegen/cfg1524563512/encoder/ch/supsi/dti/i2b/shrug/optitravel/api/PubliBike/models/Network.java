package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;
public class Network implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"name\":", 8);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Network)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Network obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj.getName() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getName());
}
stream.writeRaw(",\"id\":", 6);
stream.writeVal((int)obj.getId());
stream.writeRaw(",\"background_img\":", 18);
if (obj.getBackground_img() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getBackground_img());
}
stream.writeRaw(",\"logo_img\":", 12);
if (obj.getLogo_img() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getLogo_img());
}
stream.writeRaw(",\"sponsors\":", 12);
if (obj.getSponsors() == null) { stream.writeNull(); } else {
stream.write((byte)'[');
jsoniter_codegen.cfg1524563512.encoder.java.util.ArrayList_java.lang.String.encode_((java.util.ArrayList)obj.getSponsors(), stream);
stream.write((byte)']');
}
}
}
