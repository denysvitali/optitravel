package jsoniter_codegen.cfg1524563512.encoder;
public class int_array implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.write((byte)'[');
encode_((int[])obj, stream);
stream.write((byte)']');
}
public static void encode_(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
int[] arr = (int[])obj;
if (arr.length == 0) { return; }
int i = 0;
int e = arr[i++];
stream.writeVal((int)e);
while (i < arr.length) {
stream.write(',');
e = arr[i++];
stream.writeVal((int)e);
}
}
}
