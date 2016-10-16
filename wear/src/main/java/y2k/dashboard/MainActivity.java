package y2k.dashboard;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import y2k.dashboard.data.DashboardContentProvider;
import y2k.dashboard.data.JsonContentProvider;
import y2k.dashboard.data.Notifiable;

public class MainActivity extends WearableActivity implements Notifiable {

    private TextView contentView;
    private DashboardContentProvider contentProvider;
    private String title   = "";
    private String content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setAmbientEnabled();

        contentView = (TextView) findViewById(R.id.content);

        contentProvider = new JsonContentProvider();
        contentProvider.setContentHolder(this);
        contentProvider.update();

        updateViewsAndNotifications();
    }

    @Override
    public void onNotification() {
        title = contentProvider.getContentTitle();
        content = contentProvider.getContent();

        updateViewsAndNotifications();
    }

    private void updateViewsAndNotifications() {
        contentView.setText(content);
        contentView.invalidate();

        Context context = getApplicationContext();

        Intent mainIntent = new Intent(context, MainActivity.class);
        NotificationCompat.Action openAction = new NotificationCompat.Action.Builder(
                R.drawable.common_full_open_on_phone,
                getString(R.string.open),
                PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        ).build();

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintHideIcon(true)
                        .setBackground(BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher))
                        .addAction(openAction);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setCategory(Notification.CATEGORY_STATUS)
                .extend(wearableExtender)
                .build();

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);
    }
}
