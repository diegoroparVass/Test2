package emermedica.test2.utils;

import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by Diego Roman on 26/03/2018.
 */
public class UtilsTab {

    private static final String TAG = "UtilsTab";

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

    public static InetAddress getLocalAddress() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName("127.0.0.1");
            //addr = InetAddress.getByName("localhost");
            //addr = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error getting the network interface information");
            addr = getInetAddress();
        }
        return addr;
    }
}
