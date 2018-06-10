package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import com.jsoniter.any.Any;

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Line> lines;

    public Polygon(Any json){
        this.lines = new ArrayList<>();
        for(Any line : json.asList()){
            this.lines.add(new Line(line));
        }
    }

    public ArrayList<Line> getLines() {
        return lines;
    }
}
