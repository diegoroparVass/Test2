package emermedica.test2.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Diego Roman on 26/03/2018.
 */
public class TestReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Intent service = new Intent("com.example.broadcasttest.MonitorService");
        //Intent service = new Intent(context, MonitorService.class);
        Intent service = new Intent(context, EmermedicaServiceI.class);
        startWakefulService(context, service);
    }

}
