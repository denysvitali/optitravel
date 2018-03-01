package ch.supsi.dti.i2b.shrug.optitravel.config;

public class BuildConfig {
    private static BuildFlavour buildConfig = BuildFlavour.DEV;
    private static int MAJOR = 0;
    private static int MINOR = 1;
    private static int PATCH = 0;

    public static String getVersion(){
        return String.format("v%d.%d.%d", MAJOR, MINOR, PATCH);
    }
}
