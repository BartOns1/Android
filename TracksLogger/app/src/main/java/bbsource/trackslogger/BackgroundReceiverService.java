package bbsource.trackslogger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bbsource.trackslogger.domain.Participant;

/**
 * Created by vdabcursist on 11/10/2017.
 */

public class BackgroundReceiverService extends Service {

    String groupName="Knights saying NI";//TODO




    public BackgroundReceiverService(){
        super();
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }







    @Override
    public void onCreate(){
        Toast.makeText(getApplicationContext(),"ReceiverService Created", Toast.LENGTH_SHORT).show();
        super.onCreate();
        List<Participant> participants= DataReceiveParser.jSonParser(groupName);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(getApplicationContext(),"ReceiverService Started", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy(){
        Toast.makeText(getApplicationContext(), "ReceiverService Destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

}
