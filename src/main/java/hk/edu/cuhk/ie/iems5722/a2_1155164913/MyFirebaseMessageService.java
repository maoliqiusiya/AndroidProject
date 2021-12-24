package hk.edu.cuhk.ie.iems5722.a2_1155164913;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MyFirebaseMessageService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessageService.class.getSimpleName();
    private String ip = "34.125.85.22";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Token:" + token);

    }

    private void sendRegistrationToServer(String token) {

        try {
            URL url = new URL(ip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(30000);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            Uri.Builder builder = new Uri.Builder();
            builder.appendQueryParameter("user_id","1155164913");
            builder.appendQueryParameter("token", token);
//            builder.appendQueryParameter("name", params[3]);
//            builder.appendQueryParameter("message", params[4]);

            String query = builder.build().getEncodedQuery();
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            int response = conn.getResponseCode();
            if (response != 200){
//                Toast.makeText(getApplicationContext(),"higher version than 4.0 will not support main thread connection",Toast.LENGTH_SHORT).show();
                System.out.print("send unsuccessfully ++++++++++++++");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG, "getttttttttttttttttttttt: " + remoteMessage.getFrom());
        String id = null;
        String chatroom_name = null;
        String meg = null;
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            id = remoteMessage.getData().get("id");
            meg = remoteMessage.getData().get("meg");
            chatroom_name = remoteMessage.getData().get("chatroom_name");
            sendNotification(chatroom_name, meg, id);

        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification title: " + remoteMessage.getNotification().getTitle());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), id);
//            ChatActivity chatActivity = new ChatActivity();
//            chatActivity.sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }


    }

    public void sendNotification(String title, String context, String id) {
        String CHANNEL_ID = String.valueOf(System.currentTimeMillis());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(title)
                .setContentText(context)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setAutoCancel(true);

        Intent indent = new Intent(this, ChatActivity.class);
//        indent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        indent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        indent.addCategory(Intent.CATEGORY_LAUNCHER);
        indent.putExtra("id", id);
        indent.putExtra("name", title);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, indent, PendingIntent.FLAG_ONE_SHOT);

        builder.setContentIntent(pendingIntent);
        builder.setFullScreenIntent(pendingIntent, true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, builder.build());

    }
}
