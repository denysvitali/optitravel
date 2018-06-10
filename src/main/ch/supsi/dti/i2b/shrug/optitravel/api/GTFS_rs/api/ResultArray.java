package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api;

import com.jsoniter.any.Any;

public class ResultArray {
    public Any result;
    public Meta meta;

    public Any getResult(){
        return this.result;
    }

    public Meta getMeta() {
        return meta;
    }
}
