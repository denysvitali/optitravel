package jsoniter_codegen.cfg1524563512.decoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api;
public class Error implements com.jsoniter.spi.Decoder {
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
return (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error)existingObj);
} else {
nextToken = com.jsoniter.CodegenAccess.nextToken(iter);
if (nextToken == '}') {
return (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error)existingObj);
} else {
com.jsoniter.CodegenAccess.unreadByte(iter);
}
} // end of if end
} else { com.jsoniter.CodegenAccess.unreadByte(iter); }// end of if not quote
int _code_ = 0;
java.lang.String _message_ = null;
do {
switch (com.jsoniter.CodegenAccess.readObjectFieldAsHash(iter)) {
case -114201356: 
_code_ = (int)iter.readInt();
continue;
case 619841764: 
_message_ = (java.lang.String)iter.readString();
continue;
}
iter.skip();
} while (com.jsoniter.CodegenAccess.nextTokenIsComma(iter));
ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error obj = (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error)existingObj);
obj.code = _code_;
obj.message = _message_;
return obj;
}public java.lang.Object decode(com.jsoniter.JsonIterator iter) throws java.io.IOException {
return decode_(iter);
}
}
