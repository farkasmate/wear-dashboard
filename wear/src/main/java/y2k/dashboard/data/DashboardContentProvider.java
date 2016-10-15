package y2k.dashboard.data;

public interface DashboardContentProvider {

    public void update();

    public String getContentTitle();

    public String getContent();
}
