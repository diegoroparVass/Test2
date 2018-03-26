package emermedica.test2.socket;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashSet;

import de.greenrobot.event.EventBus;
import emermedica.test2.event.SocketMessageEvent;

import java.util.Set;

/**
 * Created by Diego Roman on 22/03/2018.
 */
public class MySocketServer extends WebSocketServer {
    private static final String TAG = "MySocketServer";

    //private WebSocket mSocket;
    private Set<WebSocket> mSocket;

    public MySocketServer(InetSocketAddress address) {
        super(address);
        mSocket = new HashSet<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        //mSocket = conn;
        Log.e(TAG, "Opening Web Socket from client-->" + conn.getRemoteSocketAddress().toString());
        mSocket.add(conn);
        Log.e(TAG, "Opened Web Socket from client-->" + conn.getRemoteSocketAddress().toString());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Log.e(TAG, "Closing Web Socket from client-->" + conn.getRemoteSocketAddress().toString());
        mSocket.remove(conn);
        Log.e(TAG, "Closed Web Socket from client-->" + conn.getRemoteSocketAddress().toString());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Log.e(TAG, "MessageGet-->["+ message+"] from client-->" + conn.getRemoteSocketAddress().toString() );
        //System.out.println("Message from client: " + message);
        for (WebSocket sock : mSocket) {
            sock.send(message);
        }

        EventBus.getDefault().post(new SocketMessageEvent(message));
        //Log.e(TAG, "onMessage-"+ message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Log.e(TAG, "onError-->["+Log.getStackTraceString(ex)+"] from client-->" + conn.getRemoteSocketAddress().toString());
        if (conn != null) {
            mSocket.remove(conn);
            // do some thing if required
        }
    }

    public void sendMessage(String message) {
        try {
            //mSocket(message);
            //Log.e(TAG, String.format("sendMessage-{0}.", message));
            Log.e(TAG, "sendMessage->" + message);

            //onMessage(mSocket,message);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "sendMessageError->" + Log.getStackTraceString(ex));
        }
    }

}

