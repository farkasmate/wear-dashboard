package y2k.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import y2k.dashboard.data.DashboardContent;
import y2k.dashboard.data.JsonContentProvider;

public class NotificationActivity extends Activity {

    private TextView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        if (! DashboardContent.isInitialized()) {
            DashboardContent.setContentProvider(new JsonContentProvider());
        }

        contentView = (TextView) findViewById(R.id.content);

        contentView.setText(DashboardContent.getContent());
    }
}
