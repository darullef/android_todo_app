package pl.wsb.todoapp.main;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import pl.wsb.todoapp.R;
import pl.wsb.todoapp.activities.MainActivity;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String todoText = intent.getStringExtra("TODO_TEXT");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "TODO_APP_NOTIFICATION_CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("It's high time to act")
                .setContentText(todoText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        builder.setContentIntent(contentIntent);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.notify(200, builder.build());
    }
}
