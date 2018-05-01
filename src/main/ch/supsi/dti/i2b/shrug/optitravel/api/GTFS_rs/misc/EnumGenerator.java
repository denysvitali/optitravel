package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.misc;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class EnumGenerator {
    public static void main(String[] args) throws FileNotFoundException {
        FileReader fr = new FileReader("./nb/denvit/gtfs/routes.json");
        BufferedReader br = new BufferedReader(fr);
        
        String json = br.lines().collect(Collectors.joining());
        JsonIterator jsonIterator = new JsonIterator();
        List<Any> coll = jsonIterator.deserialize(json).asList();

        System.out.println("public enum RouteType {");
        
        coll.stream().forEach((e) -> {
            System.out.println(String.format(
                    "%s(%s),",
                    parseDesc(e.get("description").toString()),
                    e.get("id")
            ));
        });
        System.out.println("}");
    }
    
    private static String parseDesc(String input){
        return input.replaceAll("[- ]","_")
                .replaceAll("[\\(\\)]","")
                .toUpperCase();
    }
}
