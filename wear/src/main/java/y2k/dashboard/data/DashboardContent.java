package y2k.dashboard.data;

public class DashboardContent {
    private static DashboardContentProvider provider;
    private static String title   = "";
    private static String content = "";
    private static boolean initialized = false;

    public static String getContentTitle() {
        return title;
    }

    public static String getContent() {
        return content;
    }

    public static void setContentProvider(DashboardContentProvider provider) {
        if ( ! initialized ) {
            DashboardContent.provider = provider;
            update();
            initialized = true;
        }
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void update() {
        provider.update();

        title = provider.getContentTitle();
        content = provider.getContent();
    }
}
