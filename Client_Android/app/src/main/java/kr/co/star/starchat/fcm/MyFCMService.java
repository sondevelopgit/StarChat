package kr.co.star.starchat.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFCMService extends FirebaseMessagingService {
    public static final String TAG = MyFCMService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived ID: " + remoteMessage.getMessageId());
        Log.d(TAG, "onMessageReceived DATA: " + remoteMessage.getData());
    }
}
