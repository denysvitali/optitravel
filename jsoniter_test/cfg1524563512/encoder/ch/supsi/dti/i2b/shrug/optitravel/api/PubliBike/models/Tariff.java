package jsoniter_codegen.cfg1524563512.encoder.ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;
public class Tariff implements com.jsoniter.spi.Encoder {
public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj == null) { stream.writeNull(); return; }
stream.writeRaw("{\"name\":", 8);
encode_((ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Tariff)obj, stream);
stream.write((byte)'}');
}
public static void encode_(ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Tariff obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
if (obj.getName() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getName());
}
stream.writeRaw(",\"id\":", 6);
stream.writeVal((int)obj.getId());
stream.writeRaw(",\"description\":", 15);
if (obj.getDescription() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getDescription());
}
stream.writeRaw(",\"basePrice\":", 13);
if (obj.getBasePrice() == null) { stream.writeNull(); } else {
stream.writeVal((java.lang.String)obj.getBasePrice());
}
stream.writeRaw(",\"tariffModels\":", 16);
if (obj.getTariffModels() == null) { stream.writeNull(); } else {
stream.write((byte)'[');
jsoniter_codegen.cfg1524563512.encoder.java.util.List_ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.TariffModel.encode_((java.util.List)obj.getTariffModels(), stream);
stream.write((byte)']');
}
stream.writeRaw(",\"upgradeTariffId\":", 19);
stream.writeVal((int)obj.getUpgradeTariffId());
}
}
