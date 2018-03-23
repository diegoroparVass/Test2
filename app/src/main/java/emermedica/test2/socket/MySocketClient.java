package emermedica.test2.socket;

import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import javax.json.Json;
//import javax.json.JsonArray;
//import javax.json.JsonObject;
//import javax.json.JsonReader;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import org.json.JSONArray;

/**
 * Created by Diego Roman on 23/03/2018.
 */
public class MySocketClient extends WebSocketClient{

    private static final String APPLICATION_JSON = "application/json";
    //private static final String REST_DEVICE_POST_ENDPOINT = "http://172.1.1.23:8080/test_all/";
    private ExecutorService execService = Executors.newCachedThreadPool();

    public MySocketClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        JsonReader reader = null;
        try {
            InputStream inStream = new ByteArrayInputStream(message.getBytes("UTF-8"));

            BufferedInputStream bufferedStream = new BufferedInputStream(
                    inStream);
            InputStreamReader streamReader = new InputStreamReader(
                    bufferedStream);

            reader = new JsonReader(streamReader);

            //populateTable(reader);
        } catch (Exception e) {
            Log.e("Json Sample", e.getLocalizedMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }




    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
