package jsoniter_codegen.cfg1524563512.decoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models;
public class Pagination implements com.jsoniter.spi.Decoder {
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
return (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination)existingObj);
} else {
nextToken = com.jsoniter.CodegenAccess.nextToken(iter);
if (nextToken == '}') {
return (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination)existingObj);
} else {
com.jsoniter.CodegenAccess.unreadByte(iter);
}
} // end of if end
} else { com.jsoniter.CodegenAccess.unreadByte(iter); }// end of if not quote
int _offset_ = 0;
int _limit_ = 0;
do {
switch (com.jsoniter.CodegenAccess.readObjectFieldAsHash(iter)) {
case 348705738: 
_offset_ = (int)iter.readInt();
continue;
case 853203252: 
_limit_ = (int)iter.readInt();
continue;
}
iter.skip();
} while (com.jsoniter.CodegenAccess.nextTokenIsComma(iter));
ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination obj = (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination)existingObj);
obj.offset = _offset_;
obj.limit = _limit_;
return obj;
}public java.lang.Object decode(com.jsoniter.JsonIterator iter) throws java.io.IOException {
return decode_(iter);
}
}
