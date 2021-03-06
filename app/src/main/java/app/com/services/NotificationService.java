package app.com.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;
import app.com.notifyme.NoticeDashboard;
import app.com.notifyme.R;

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
       /* pref = getApplicationContext().getSharedPreferences("UserVals", 0); // 0 - for private mode
        editor = pref.edit();
        editor.putString("token",s);
        editor.commit();*/

    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
       /* pref = getApplicationContext().getSharedPreferences("UserVals", 0); // 0 - for private mode
        editor = pref.edit();
        notificationModel = new NotificationModel();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);
        int random = new Random().nextInt(1000000);
        notificationModel.setTitle(remoteMessage.getData().get("title"));
        notificationModel.setDesc(remoteMessage.getData().get("body"));
        notificationModel.setDate(strDate);
        notificationModel.setNotificationID(random);
        list.add(notificationModel);
        Gson record1 = new Gson();
        String jsonText = pref.getString("notificationdata", null);
        String record;
        if(jsonText!=null) {
            Type type = new TypeToken<List<NotificationModel>>() {
            }.getType();
            List<NotificationModel> notificationList = new ArrayList<NotificationModel>();
            notificationList.addAll((Collection<? extends NotificationModel>) record1.fromJson(jsonText, type));
            notificationList.add(notificationModel);
             record = gson.toJson(notificationList);
            editor.putString("notificationdata",record);
            editor.apply();
        }
        else{
            record = gson.toJson(list);
            editor.putString("notificationdata",record);
            editor.apply();
        }*/

        Intent intent = new Intent(this, NoticeDashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 /* request code */, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        String channelId = "Default";
        long[] pattern = {500,500,500,500,500};
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notification_icon)
                .setVibrate(pattern)
                .setSound(defaultSoundUri)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("body"))
                .setLights(getResources().getColor(R.color.colorAccent),1,1)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }
    }

