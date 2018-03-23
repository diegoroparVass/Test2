package emermedica.test2;

import android.app.Application;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import de.greenrobot.event.EventBus;
import emermedica.test2.event.SocketMessageEvent;
import emermedica.test2.socket.MySocketServer;

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
        InetAddress inetAddress = getInetAddress();
        if (inetAddress == null) {
            Log.e(TAG, "Unable to lookup IP address");
            return;
        }

        Log.e(TAG, "IP Server-->"+inetAddress.getHostAddress());

        mServer = new MySocketServer(new InetSocketAddress(inetAddress.getHostAddress(), SERVER_PORT));
        Log.e(TAG, "MySocketServer-->"+mServer.getAddress());
        mServer.start();
        Log.e(TAG, "MySocketServer,Server Started-->"+inetAddress.getHostAddress());
        //mServer.sendMessage("Server Start Listen!");
    }

    public static InetAddress getInetAddress() {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface networkInterface = (NetworkInterface) en.nextElement();

                for (Enumeration enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();

                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            Log.e(TAG, "Error getting the network interface information");
        }

        return null;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(SocketMessageEvent event) {
        String message = event.getMessage();
        //mServer.sendMessage("echo: " + message);
        Log.e(TAG, "onEvent_Message-->"+message);
    }
}
