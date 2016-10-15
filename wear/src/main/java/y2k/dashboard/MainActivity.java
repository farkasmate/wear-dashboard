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

import y2k.dashboard.data.DashboardContent;
import y2k.dashboard.data.JsonContentProvider;

public class MainActivity extends WearableActivity {

    private TextView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setAmbientEnabled();

        if (!DashboardContent.isInitialized()) {
            DashboardContent.setContentProvider(new JsonContentProvider());
        }

        contentView = (TextView) findViewById(R.id.content);

        contentView.setText(DashboardContent.getContent());

        Context context = getApplicationContext();
        Intent displayIntent = new Intent(context, NotificationActivity.class);

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
                .setContentTitle(DashboardContent.getContentTitle())
                .setContentText(DashboardContent.getContent())
                .setCategory(Notification.CATEGORY_STATUS)
                .extend(wearableExtender)
                .build();
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);
    }
}
