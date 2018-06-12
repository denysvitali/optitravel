package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;
public class TariffModel implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"vehicleType\":", 15);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.TariffModel)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.TariffModel obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj.getVehicleType() == null) { stream.writeNull(); } else {
stream.writeRaw("{\"name\":", 8);
jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.VehicleType.encode_((ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.VehicleType)obj.getVehicleType(), stream);
stream.write((byte)'}');
}
stream.writeRaw(",\"fixedPrice\":", 14);
if (obj.getFixedPrice() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getFixedPrice());
}
stream.writeRaw(",\"fixedPriceDescription\":", 25);
if (obj.getFixedPriceDescription() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getFixedPriceDescription());
}
stream.writeRaw(",\"varPrice\":", 12);
if (obj.getVarPrice() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getVarPrice());
}
stream.writeRaw(",\"varPriceDescription\":", 23);
if (obj.getVarPriceDescription() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getVarPriceDescription());
}
stream.writeRaw(",\"maxPrice\":", 12);
if (obj.getMaxPrice() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getMaxPrice());
}
stream.writeRaw(",\"maxPriceDescription\":", 23);
if (obj.getMaxPriceDescription() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getMaxPriceDescription());
}
}
}
