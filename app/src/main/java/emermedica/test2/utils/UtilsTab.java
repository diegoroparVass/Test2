package emermedica.test2.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import emermedica.test2.R;

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

    public static void messageNotify(String message,Exception exG,String tagShow,Context contentG)
    {
        try {

            if(TextUtils.isEmpty(message)) {

                //Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
                message = "Cadena Vacia para Log!";
            }

            if(exG!=null)
            {
                message = Log.getStackTraceString(exG);
            }

            Log.e(tagShow, message);
            Log.println(1, tagShow, message);

            Toast.makeText(contentG,message,Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            if(e!=null)
            {
                message = Log.getStackTraceString(exG);
            }

            Log.e(tagShow, message);
            Log.println(1,tagShow,message);
        }
    }
}
