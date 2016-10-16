package y2k.dashboard.data;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonContentProvider implements DashboardContentProvider {

    private JSONObject json;
    private String title   = "NO TITLE";
    private String content = "NO CONTENT";
    private boolean updating = false;
    private Notifiable contentHolder;

    @Override
    public void update() {
        if (!updating) {
            //new DownloadTask().execute("https://indeed.sch.bme.hu:3000/test.json");
            new DownloadTask().execute("http://indeed.sch.bme.hu/test.json");
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

    @Override
    public void setContentHolder(Notifiable notifiable) {
        contentHolder = notifiable;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "DownloadTask";

        @Override
        protected String doInBackground(String... endpoints) {
            Log.d(TAG, "Starting to download (" + endpoints[0] + ") in the background.");

            InputStream is = null;
            // FIXME
            int len = 500;
            String httpContent = "{}";

            try {
                URL url = new URL(endpoints[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                conn.connect();
                int response = conn.getResponseCode();
                Log.d(TAG, "The response is: " + response);
                is = conn.getInputStream();

                Reader reader = null;
                reader = new InputStreamReader(is, "UTF-8");
                char[] buffer = new char[len];
                reader.read(buffer);

                is.close();

                httpContent = new String(buffer);
            } catch (IOException e) {
                Log.e(TAG, "Can't download URL (" + endpoints[0] + "): " + e.getMessage());
            }

            return httpContent;
        }

        @Override
        protected void onPostExecute(String httpContent) {
            try {
                json = new JSONObject(httpContent);
                title = json.getString("title");
                content = json.getString("content");
            } catch (JSONException e) {
                e.printStackTrace();
                content = "Can't parse content.";
            }

            if (contentHolder != null) {
                contentHolder.onNotification();
            }
        }
    }
}
