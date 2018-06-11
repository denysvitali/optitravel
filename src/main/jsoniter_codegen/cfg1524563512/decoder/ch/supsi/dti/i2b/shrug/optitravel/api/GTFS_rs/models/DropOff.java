package jsoniter_codegen.cfg1524563512.decoder.ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;
public class DropOff implements com.jsoniter.spi.Decoder {
public static java.lang.Object decode_(com.jsoniter.JsonIterator iter) throws java.io.IOException { if (iter.readNull()) { return null; }
com.jsoniter.spi.Slice field = com.jsoniter.CodegenAccess.readSlice(iter);
switch (field.len()) {
case 18: 
if (
field.at(0)==82 && 
field.at(1)==101 && 
field.at(2)==103 && 
field.at(3)==117 && 
field.at(4)==108 && 
field.at(5)==97 && 
field.at(6)==114 && 
field.at(7)==108 && 
field.at(8)==121 && 
field.at(9)==83 && 
field.at(10)==99 && 
field.at(11)==104 && 
field.at(12)==101 && 
field.at(13)==100 && 
field.at(14)==117 && 
field.at(15)==108 && 
field.at(16)==101 && 
field.at(17)==100
) {
return ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.DropOff.RegularlyScheduled;
}
break;
case 21: 
if (
field.at(0)==77 && 
field.at(1)==117 && 
field.at(2)==115 && 
field.at(3)==116 && 
field.at(4)==65 && 
field.at(5)==114 && 
field.at(6)==114 && 
field.at(7)==97 && 
field.at(8)==110 && 
field.at(9)==103 && 
field.at(10)==101 && 
field.at(11)==87 && 
field.at(12)==105 && 
field.at(13)==116 && 
field.at(14)==104 && 
field.at(15)==65 && 
field.at(16)==103 && 
field.at(17)==101 && 
field.at(18)==110 && 
field.at(19)==99 && 
field.at(20)==121
) {
return ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.DropOff.MustArrangeWithAgency;
}
break;
case 24: 
if (
field.at(0)==77 && 
field.at(1)==117 && 
field.at(2)==115 && 
field.at(3)==116 && 
field.at(4)==67 && 
field.at(5)==111 && 
field.at(6)==111 && 
field.at(7)==114 && 
field.at(8)==100 && 
field.at(9)==105 && 
field.at(10)==110 && 
field.at(11)==97 && 
field.at(12)==116 && 
field.at(13)==101 && 
field.at(14)==87 && 
field.at(15)==105 && 
field.at(16)==116 && 
field.at(17)==104 && 
field.at(18)==65 && 
field.at(19)==103 && 
field.at(20)==101 && 
field.at(21)==110 && 
field.at(22)==99 && 
field.at(23)==121
) {
return ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.DropOff.MustCoordinateWithAgency;
}
break;
case 12: 
if (
field.at(0)==78 && 
field.at(1)==111 && 
field.at(2)==116 && 
field.at(3)==65 && 
field.at(4)==118 && 
field.at(5)==97 && 
field.at(6)==105 && 
field.at(7)==108 && 
field.at(8)==97 && 
field.at(9)==98 && 
field.at(10)==108 && 
field.at(11)==101
) {
return ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.DropOff.NotAvailable;
}
break;

}
throw iter.reportError("decode enum", field + " is not valid enum for ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.DropOff");
}public java.lang.Object decode(com.jsoniter.JsonIterator iter) throws java.io.IOException {
return decode_(iter);
}
}
