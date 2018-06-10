package ch.supsi.dti.i2b.shrug.optitravel.config;

public class BuildConfig {
    private static BuildFlavour buildConfig = BuildFlavour.BETA;
    public static final boolean USE_GTFS_REMOTE = true;
    private static int MAJOR = 1;
    private static int MINOR = 0;
    private static int PATCH = 0;

    public static String getVersion(){
        return String.format("v%d.%d.%d", MAJOR, MINOR, PATCH);
    }
    public static boolean isDev(){
        return buildConfig == BuildFlavour.DEV;
    }
}
