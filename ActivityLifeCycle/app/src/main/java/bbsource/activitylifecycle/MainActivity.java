package bbsource.activitylifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private String TAG="Android BBsource";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy invoked");
    }
}

