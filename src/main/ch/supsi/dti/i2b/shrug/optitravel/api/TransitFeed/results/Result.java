package ch.supsi.dti.i2b.shrug.optitravel.api.TransitFeed.results;

import com.jsoniter.any.Any;

public class Result<T> {
    private String status;
    private int ts;
    private String msg;
    private Any results;

    public Result(){

    }

    public Any getResults(){
        return results;
    }
}
