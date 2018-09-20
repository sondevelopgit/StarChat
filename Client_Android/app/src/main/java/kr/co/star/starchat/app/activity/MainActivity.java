package kr.co.star.starchat.app.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

import kr.co.star.starchat.R;

public class MainActivity extends AppCompatActivity {
    private FirebaseRemoteConfig firebaseRemoteConfig;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // Remote Config 초기화

        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings;
        firebaseRemoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build();
        // Remote Config 설정

        Map<String, Object> defaultCongfigMap = new HashMap<>();
        defaultCongfigMap.put("message_length", 10L);
        // 인터넷 연결이 안 되어있을 때 기본값 설정

        firebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        firebaseRemoteConfig.setDefaults(defaultCongfigMap);
        // config 설정과 기본값 설정

        fetchConfig();
        // 원격 구성 가져오기
    }

    public void fetchConfig() {
        long cacheExpiration = 3600; // 1시간

        if(firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        // 개발자 모드일 경우 0초

        firebaseRemoteConfig.fetch(cacheExpiration).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseRemoteConfig.activateFetched();
                applyRetrievedLengthLimit();
            }
            // 원격 가져오기 성공
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error fetching config: " + e.getMessage());
                applyRetrievedLengthLimit();
            }
            // 원격 가져오기 실패
        });
    }
    // 원격의 구성을 가져온다.

    private void applyRetrievedLengthLimit() {
        Long messageLength = firebaseRemoteConfig.getLong("message_length");
        Log.d(TAG, "메시지 길이: " + messageLength);
    }
    // 서버에서 가져오거나 캐시된 값을 가져온다.


}
