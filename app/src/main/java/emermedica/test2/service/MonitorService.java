package emermedica.test2.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Diego Roman on 26/03/2018.
 */
public class MonitorService extends IntentService {

    public MonitorService(String name) {
        super(name);
    }

    public MonitorService()
    {
        super("MonitorService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            TestReceiver.completeWakefulIntent(intent);
        }

    }

}
