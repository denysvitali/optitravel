package jsoniter_codegen.cfg1524563512.decoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;
public class TripTimeStop implements com.jsoniter.spi.Decoder {
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
return (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop)existingObj);
} else {
nextToken = com.jsoniter.CodegenAccess.nextToken(iter);
if (nextToken == '}') {
return (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop)existingObj);
} else {
com.jsoniter.CodegenAccess.unreadByte(iter);
}
} // end of if end
} else { com.jsoniter.CodegenAccess.unreadByte(iter); }// end of if not quote
java.lang.String _trip_ = null;
java.lang.String _time_ = null;
java.lang.String _next_stop_ = null;
do {
switch (com.jsoniter.CodegenAccess.readObjectFieldAsHash(iter)) {
case -1854370539: 
_next_stop_ = (java.lang.String)iter.readString();
continue;
case 1564253156: 
_time_ = (java.lang.String)iter.readString();
continue;
case 1888760810: 
_trip_ = (java.lang.String)iter.readString();
continue;
}
iter.skip();
} while (com.jsoniter.CodegenAccess.nextTokenIsComma(iter));
ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop obj = (existingObj == null ? new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop() : (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop)existingObj);
obj.trip = _trip_;
obj.time = _time_;
obj.next_stop = _next_stop_;
return obj;
}public java.lang.Object decode(com.jsoniter.JsonIterator iter) throws java.io.IOException {
return decode_(iter);
}
}
