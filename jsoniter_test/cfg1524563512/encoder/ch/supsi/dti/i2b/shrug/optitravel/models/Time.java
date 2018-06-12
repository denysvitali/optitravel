package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.models;
public class Time implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"hour\":", 8);
encode_((ch.supsi.dti.i2b.shrug.optitravel.models.Time)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.models.Time obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
stream.writeVal((int)obj.getHour());
stream.writeRaw(",\"minute\":", 10);
stream.writeVal((int)obj.getMinute());
stream.writeRaw(",\"second\":", 10);
stream.writeVal((int)obj.getSecond());
}
}
