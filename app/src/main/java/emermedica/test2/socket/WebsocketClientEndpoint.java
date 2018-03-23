package emermedica.test2.socket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Created by Diego Roman on 23/03/2018.
 */
public class WebsocketClientEndpoint extends WebSocketClient  {
    private static final String TAG = "WebsocketClientEndpoint";

    public WebsocketClientEndpoint(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public WebsocketClientEndpoint(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("new connection opened");
        Log.e(TAG, "Opening Web Socket");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
        Log.e(TAG, "Closing Web Socket");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received message: " + message);
        Log.e(TAG, "Message Web Socket");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
        Log.e(TAG, "Error Web Socket");
    }


}
