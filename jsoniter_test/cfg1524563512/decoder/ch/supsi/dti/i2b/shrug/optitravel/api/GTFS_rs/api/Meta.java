package jsoniter_codegen.cfg1524563512.decoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api;
public class Meta implements com.jsoniter.spi.Decoder {
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
return (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta)existingObj);
} else {
nextToken = com.jsoniter.CodegenAccess.nextToken(iter);
if (nextToken == '}') {
return (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta)existingObj);
} else {
com.jsoniter.CodegenAccess.unreadByte(iter);
}
} // end of if end
} else { com.jsoniter.CodegenAccess.unreadByte(iter); }// end of if not quote
boolean _success_ = false;
ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error _error_ = null;
ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination _pagination_ = null;
do {
switch (com.jsoniter.CodegenAccess.readObjectFieldAsHash(iter)) {
case 563185489: 
_error_ = (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error)jsoniter_codegen.cfg1524563512.decoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error.decode_(iter);
continue;
case 979353360: 
_success_ = (boolean)iter.readBoolean();
continue;
case 1186993097: 
_pagination_ = (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination)jsoniter_codegen.cfg1524563512.decoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination.decode_(iter);
continue;
}
iter.skip();
} while (com.jsoniter.CodegenAccess.nextTokenIsComma(iter));
ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta obj = (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta)existingObj);
obj.success = _success_;
obj.error = _error_;
obj.pagination = _pagination_;
return obj;
}public java.lang.Object decode(com.jsoniter.JsonIterator iter) throws java.io.IOException {
return decode_(iter);
}
}
