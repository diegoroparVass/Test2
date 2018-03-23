package emermedica.test2;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.net.Uri;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;

import java.net.InetAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener {

    private ListView lv;
    String[] ListElements = new String[] {
            "Android",
            "PHP"
    };
    private List<String> ListElementsArrayList;
    private ArrayAdapter<String> adapter;

    private static final String TAG = "MainActivity";
    private static final int SERVER_PORT = 12345;

    public MainActivity() {
        ListElementsArrayList = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Button button = (Button) findViewById(R.id.button1);
            //button.setOnClickListener(this);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //onClick(v);
                    messageNotify("Click1",v,null);
                    try {

                        final View vF = v;


                        InetAddress vAdd = MyApplication.getInetAddress();
                        String strAdd = vAdd.getHostAddress().toString();
                        messageNotify("IPAddresInfo - " + strAdd,vF,null);

                        String destUri = "ws://"+strAdd+":"+SERVER_PORT;
                        messageNotify("destUri-->" + destUri,vF,null);

                        URI ur = URI.create(destUri);

                        WebSocketClient cliente = new WebSocketClient(ur) {
                            @Override
                            public void onOpen(ServerHandshake handshakedata) {
                                messageNotify("Socket Open!",vF,null);
                            }

                            @Override
                            public void onMessage(String message) {
                                messageNotify(message,vF,null);
                            }

                            @Override
                            public void onClose(int code, String reason, boolean remote) {
                                messageNotify("Socket Closed!",vF,null);
                            }

                            @Override
                            public void onError(Exception ex) {
                                messageNotify(ex.getMessage(),vF,ex);
                            }
                        };

                       cliente.send("Hola mundo!");
                    }
                    catch (Exception ex) {

                        messageNotify("Error",v,ex);
                    }

                }
            });

            Button button2 = (Button) findViewById(R.id.button2);
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onClick2(v);
                }
            });

            lv = (ListView) findViewById(R.id.listView1);
            ListElementsArrayList = new ArrayList<String>
                    (Arrays.asList(ListElements));

            adapter = new ArrayAdapter<String>
                    (MainActivity.this, android.R.layout.simple_list_item_1,
                            ListElementsArrayList);

            lv.setAdapter(adapter);
        } catch (Exception ex)
            {
                messageNotify(ex.getMessage(),null,ex);
            }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void messageNotify(String message,View v,Exception exG)
    {
        if(TextUtils.isEmpty(message)) {

            //Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
            message = "Cadena Vacia para Log!";
        }

        if(exG!=null)
        {
            message = Log.getStackTraceString(exG);
        }

        Log.e(TAG, message);
        Log.println(1, TAG, message);

        if(v!=null){
            Toast.makeText(v.getContext(),message,Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }
        //ListView lv = (ListView)findViewById(R.id.listView1);
        try {
            ListElementsArrayList.add(message);
            adapter = new ArrayAdapter<String>
                    (MainActivity.this, android.R.layout.simple_list_item_1,
                            ListElementsArrayList);
            lv.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
            Log.println(1,TAG,e.getMessage());
        }
    }

    public void onClick(View v){
        messageNotify("Click1",v,null);
        try {

            final View vF = v;
        String destUri = "ws://echo.websocket.org";
        URI ur = URI.create(destUri);

        WebSocketClient cliente = new WebSocketClient(ur) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                messageNotify("Socket Open!",vF,null);
            }

            @Override
            public void onMessage(String message) {
                messageNotify(message,vF,null);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                messageNotify("Socket Closed!",vF,null);
            }

            @Override
            public void onError(Exception ex) {
                messageNotify(ex.getMessage(),vF,null);
            }
        };

            cliente.send("Hola mundo!");

        }
        catch (Exception ex) {
            messageNotify(ex.getMessage(),v,ex);
        }

    }

    public void onClick2(View v)
    {
        messageNotify("Click2",v,null);

    }
}
