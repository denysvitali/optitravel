package jsoniter_codegen.cfg1524563512.decoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;
public class StopTimes implements com.jsoniter.spi.Decoder {
public static java.lang.Object decode_(com.jsoniter.JsonIterator iter) throws java.io.IOException { java.lang.Object existingObj = com.jsoniter.CodegenAccess.resetExistingObject(iter);
if (iter.readNull()) { return null; }
ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTimes obj = (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTimes() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTimes)existingObj);
if (!com.jsoniter.CodegenAccess.readObjectStart(iter)) {
return obj;
}
com.jsoniter.spi.Slice field = com.jsoniter.CodegenAccess.readObjectFieldAsSlice(iter);
boolean once = true;
while (once) {
once = false;
iter.skip();
}
while (com.jsoniter.CodegenAccess.nextToken(iter) == ',') {
field = com.jsoniter.CodegenAccess.readObjectFieldAsSlice(iter);
iter.skip();
}
return obj;
}public java.lang.Object decode(com.jsoniter.JsonIterator iter) throws java.io.IOException {
return decode_(iter);
}
}
