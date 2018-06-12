package jsoniter_codegen.cfg1524563512.decoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api;
public class ResultArray implements com.jsoniter.spi.Decoder {
public static java.lang.Object decode_(com.jsoniter.JsonIterator iter) throws java.io.IOException { java.lang.Object existingObj = com.jsoniter.CodegenAccess.resetExistingObject(iter);
byte nextToken = com.jsoniter.CodegenAccess.readByte(iter);
if (nextToken != '{') {
if (nextToken == 'n') {
com.jsoniter.CodegenAccess.skipFixedBytes(iter, 3);
return null;
} else {
nextToken = com.jsoniter.CodegenAccess.nextToken(iter);
if (nextToken == 'n') {
com.jsoniter.CodegenAccess.skipFixedBytes(iter, 3);
return null;
}
} // end of if null
} // end of if {
nextToken = com.jsoniter.CodegenAccess.readByte(iter);
if (nextToken != '"') {
if (nextToken == '}') {
return (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray)existingObj);
} else {
nextToken = com.jsoniter.CodegenAccess.nextToken(iter);
if (nextToken == '}') {
return (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray)existingObj);
} else {
com.jsoniter.CodegenAccess.unreadByte(iter);
}
} // end of if end
} else { com.jsoniter.CodegenAccess.unreadByte(iter); }// end of if not quote
com.jsoniter.any.Any _result_ = null;
ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta _meta_ = null;
do {
switch (com.jsoniter.CodegenAccess.readObjectFieldAsHash(iter)) {
case -2114039976: 
_meta_ = (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta)jsoniter_codegen.cfg1524563512.decoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta.decode_(iter);
continue;
case 171406884: 
_result_ = (com.jsoniter.any.Any)iter.readAny();
continue;
}
iter.skip();
} while (com.jsoniter.CodegenAccess.nextTokenIsComma(iter));
ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray obj = (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray)existingObj);
obj.result = _result_;
obj.meta = _meta_;
return obj;
}public java.lang.Object decode(com.jsoniter.JsonIterator iter) throws java.io.IOException {
return decode_(iter);
}
}
