package ch.supsi.dti.i2b.shrug.optitravel;
import spark.Spark;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        Spark.staticFileLocation("/public/"); // Static files
        get("/", (req, res) -> "Hello World");
    }
}
