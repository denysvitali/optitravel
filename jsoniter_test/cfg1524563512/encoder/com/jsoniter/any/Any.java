package jsoniter_codegen.cfg1524563512.encoder.com.jsoniter.any;
public class Any implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.write((byte)'{', (byte)'}');
encode_((com.jsoniter.any.Any)obj, stream);
}
public static void encode_(com.jsoniter.any.Any obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
}
}
