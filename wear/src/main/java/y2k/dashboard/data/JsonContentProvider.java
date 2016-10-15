package y2k.dashboard.data;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonContentProvider implements DashboardContentProvider {

    private JSONObject json;
    private String title   = "NO TITLE";
    private String content = "NO CONTENT";

    @Override
    public void update() {
        String httpContent = "{'title': 'MyTitle', 'content': 'MyContent\nis a great content\nbecause it has a single very long line, but in general\nit is trying\nto be short.\n\nWith more\nor less\nsuccess...\n\nAlso it would be\nnice to test\nscrolling view.'}";

        try {
            json = new JSONObject(httpContent);
            title = json.getString("title");
            content = json.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getContentTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }
}
