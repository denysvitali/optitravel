package jsoniter_codegen.cfg1524563512.decoder.java.util.Map_java.lang.String_java.lang;
public class Object implements com.jsoniter.spi.Decoder {
public static java.lang.Object decode_(com.jsoniter.JsonIterator iter) throws java.io.IOException { java.util.HashMap map = (java.util.HashMap)com.jsoniter.CodegenAccess.resetExistingObject(iter);
if (iter.readNull()) { return null; }
if (map == null) { map = new java.util.HashMap(); }
if (!com.jsoniter.CodegenAccess.readObjectStart(iter)) {
return map;
}
do {
java.lang.Object mapKey = com.jsoniter.CodegenAccess.readObjectFieldAsString(iter);
map.put(mapKey, (java.lang.Object)iter.read());
} while (com.jsoniter.CodegenAccess.nextToken(iter) == ',');
return map;
}public java.lang.Object decode(com.jsoniter.JsonIterator iter) throws java.io.IOException {
return decode_(iter);
}
}
