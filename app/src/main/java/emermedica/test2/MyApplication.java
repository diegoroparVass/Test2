package emermedica.test2;

import android.app.Application;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import de.greenrobot.event.EventBus;
import emermedica.test2.event.SocketMessageEvent;
import emermedica.test2.socket.MySocketServer;
import emermedica.test2.utils.UtilsTab;

/**
 * Created by Diego Roman on 22/03/2018.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static final int SERVER_PORT = 12345;

    private MySocketServer mServer;

    @Override
    public void onCreate() {
        super.onCreate();

        startServer();
        EventBus.getDefault().register(this);
    }

    private void startServer() {
        InetAddress inetAddress = UtilsTab.getInetAddress();
        InetAddress addr = UtilsTab.getLocalAddress();

        if (inetAddress == null && addr == null) {
            Log.e(TAG, "Unable to lookup IP address and local");
            return;
        }

        if (inetAddress != null){
        Log.e(TAG, "IP Server-->"+inetAddress.getHostAddress());}

        if(addr==null)
        {
            addr=inetAddress;
        }


        mServer = new MySocketServer(new InetSocketAddress(addr.getHostAddress(), SERVER_PORT));
        Log.e(TAG, "MySocketServer-->"+mServer.getAddress());
        mServer.start();
        Log.e(TAG, "MySocketServer,Server Started-->"+mServer.getAddress());
        //mServer.sendMessage("Server Start Listen!");
    }



    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(SocketMessageEvent event) {
        String message = event.getMessage();
        //mServer.sendMessage("echo: " + message);
        Log.e(TAG, "onEvent_Message-->"+message);
    }
}
