/**
 * Contains the push receiver, an extension of the broadcast receiver.
 */
package com.example.groupproject_g3.services;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.activities.AuthorizationActivity;
import com.example.groupproject_g3.chat.fragments.ChatMessage;

import org.json.JSONException;
import me.pushy.sdk.Pushy;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

public class PushReceiver extends BroadcastReceiver {

    public static final String RECEIVED_NEW_MESSAGE = "new message from pushy";

    public static final String CONTACTS_UPDATED = "contacts page update from push";

    public static final String CHATS_UPDATED = "chats page update from push";

    private static final String CHANNEL_ID = "1";

    @Override
    public void onReceive(Context context, Intent intent) {
        String typeOfMessage = intent.getStringExtra("type");
        if (typeOfMessage.equals("contacts")) {
            handleContactsNotification(context, intent);
        } else if (typeOfMessage.equals("msg")) {
            handleChatNotification(context, intent);
        } else if (typeOfMessage.equals("chat")){
            handleChatsUpdateNotification(context,intent);
        }
    }

    /**
     * Handles an incoming contacts notification. Updates all contacts information.
     * @param context the context of the message.
     * @param intent the intent of the message.
     */
    private void handleContactsNotification(Context context, Intent intent) {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
            //app is in the foreground so send the message to the active Activities
            Log.d("PUSHY", "Contacts notification recieved in foreground: ");

            //create an Intent to broadcast a message to other parts of the app.
            Intent i = new Intent(CONTACTS_UPDATED);
            i.putExtras(intent.getExtras());
            context.sendBroadcast(i);
        } else {
            //app is in the background so create and post a notification
            Log.d("PUSHY", "Contacts info updated in background");

            Intent i = new Intent(context, AuthorizationActivity.class);
            i.putExtras(intent.getExtras());

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    i, PendingIntent.FLAG_UPDATE_CURRENT);

            //research more on notifications the how to display them
            //https://developer.android.com/guide/topics/ui/notifiers/notifications
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_chat_notification)
                    .setContentTitle("Contacts Notification")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);

            // Automatically configure a ChatMessageNotification Channel for devices running Android O+
            Pushy.setNotificationChannel(builder, context);

            // Get an instance of the NotificationManager service
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

            // Build the notification and display it
            notificationManager.notify(1, builder.build());
        }
    }

    /**
     * Handles an incoming chats notification. Updates all chat information.
     * @param context the context of the message.
     * @param intent the intent of the message.
     */
    private void handleChatsUpdateNotification(final Context context, final Intent intent) {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
            //app is in the foreground so send the message to the active Activities
            Log.d("PUSHY", "Chats notification recieved in foreground: ");

            //create an Intent to broadcast a message to other parts of the app.
            Intent i = new Intent(CHATS_UPDATED);
            i.putExtras(intent.getExtras());
            context.sendBroadcast(i);
        } else {
            //app is in the background so create and post a notification
            Log.d("PUSHY", "Chats info updated in background");

            Intent i = new Intent(context, AuthorizationActivity.class);
            i.putExtras(intent.getExtras());

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    i, PendingIntent.FLAG_UPDATE_CURRENT);

            //research more on notifications the how to display them
            //https://developer.android.com/guide/topics/ui/notifiers/notifications
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_chat_notification)
                    .setContentTitle("Chats Notification")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);

            // Automatically configure a ChatMessageNotification Channel for devices running Android O+
            Pushy.setNotificationChannel(builder, context);

            // Get an instance of the NotificationManager service
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

            // Build the notification and display it
            notificationManager.notify(1, builder.build());
        }
    }

    /**
     * Handles an incoming chat notification.
     * @param context the context of the message.
     * @param intent the intent of the message.
     */
    private void handleChatNotification(Context context, Intent intent) {
        int chatId = -1;
        ChatMessage message = null;
        try {
            message = ChatMessage.createFromJsonString(intent.getStringExtra("message"));
            chatId = intent.getIntExtra("chatid", -1);
        } catch (JSONException e) {
            //Web service sent us something unexpected...I can't deal with this.
            throw new IllegalStateException("Error from Web Service. Contact Dev Support");
        }

        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
            //app is in the foreground so send the message to the active Activities
            Log.d("PUSHY", "Chat received in foreground: " + message);

            //create an Intent to broadcast a message to other parts of the app.
            Intent i = new Intent(RECEIVED_NEW_MESSAGE);
            i.putExtra("chatMessage", message);
            i.putExtra("chatid", chatId);
            i.putExtras(intent.getExtras());

            context.sendBroadcast(i);
        } else {
            //app is in the background so create and post a notification
            Log.d("PUSHY", "Message received in background: " + message.getMessage());

            Intent i = new Intent(context, AuthorizationActivity.class);
            i.putExtras(intent.getExtras());

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    i, PendingIntent.FLAG_UPDATE_CURRENT);

            //research more on notifications the how to display them
            //https://developer.android.com/guide/topics/ui/notifiers/notifications
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_chat_notification)
                    .setContentTitle("Message from: " + message.getSender())
                    .setContentText(message.getMessage())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);

            // Automatically configure a ChatMessageNotification Channel for devices running Android O+
            Pushy.setNotificationChannel(builder, context);

            // Get an instance of the NotificationManager service
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

            // Build the notification and display it
            notificationManager.notify(1, builder.build());
        }
    }
}
