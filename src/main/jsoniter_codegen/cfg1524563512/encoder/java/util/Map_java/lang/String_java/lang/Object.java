package jsoniter_codegen.cfg1524563512.encoder.java.util.Map_java.lang.String_java.lang;
public class Object implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.write((byte)'{');
encode_((java.util.Map)obj, stream);
stream.write((byte)'}');
}
public static void encode_(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
java.util.Map map = (java.util.Map)obj;
java.util.Iterator iter = map.entrySet().iterator();
if(!iter.hasNext()) { return; }
java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
stream.writeVal((java.lang.String)entry.getKey());
stream.write(':');
if (entry.getValue() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.Object)entry.getValue());
}
while(iter.hasNext()) {
entry = (java.util.Map.Entry)iter.next();
stream.write(',');
stream.writeVal((java.lang.String)entry.getKey());
stream.write(':');
if (entry.getValue() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.Object)entry.getValue());
}
}
}
}
