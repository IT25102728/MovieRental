package util;

import jakarta.servlet.ServletContext;

/**
 * AppConfig - Centralized path configuration for all data files
 * Call AppConfig.init(servletContext) in your context listener or first servlet
 */
public class AppConfig {

    private static String dataDir = "";

    /**
     * Initialize with the real path from the servlet context.
     * Called once from the context listener at startup.
     */
    public static void init(ServletContext ctx) {
        // Store data files outside webapp root in a /data directory
        dataDir = ctx.getRealPath("/") + "data" + java.io.File.separator;
        java.io.File dir = new java.io.File(dataDir);
        if (!dir.exists()) dir.mkdirs();
        System.out.println("[AppConfig] Data directory: " + dataDir);
    }

    public static String getDataDir()       { return dataDir; }
    public static String getUsersFile()     { return dataDir + "users.txt"; }
    public static String getMoviesFile()    { return dataDir + "movies.txt"; }
    public static String getRentalsFile()   { return dataDir + "rentals.txt"; }
    public static String getReviewsFile()   { return dataDir + "reviews.txt"; }
    public static String getAdminsFile()    { return dataDir + "admins.txt"; }
    public static String getPaymentsFile()  { return dataDir + "payments.txt"; }
}
