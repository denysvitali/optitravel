package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.geography;
public class Coordinate implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"lat\":", 7);
encode_((ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
stream.writeVal((double)obj.getLat());
stream.writeRaw(",\"lng\":", 7);
stream.writeVal((double)obj.getLng());
}
}
