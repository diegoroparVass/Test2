package emermedica.test2;

import android.app.Activity;
import android.os.Build;
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
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import emermedica.test2.socket.WebsocketClientEndpoint;

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

    private WebSocketClient mWebSocketClient;
    String destUri = "";

    public MainActivity() {
        ListElementsArrayList = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView serverIp = (TextView)findViewById(R.id.serverIpTxt);
        TextView serverPort = (TextView)findViewById(R.id.serverPortText);

        TextView userName = (TextView)findViewById(R.id.userText);
        TextView userPwd = (TextView)findViewById(R.id.passwordText);

        userName.setText("DiegoR");
        userPwd.setText("1234@");

        InetAddress vAdd = MyApplication.getInetAddress();
        String strAdd = vAdd.getHostAddress().toString();

        serverIp.setText(strAdd);
        serverPort.setText("12345");

        messageNotify("IPAddresInfo Local - " + strAdd, null, null);
        concatUriCustomServer();

        try {

            Button button = (Button) findViewById(R.id.button1);
            //button.setOnClickListener(this);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //onClick(v);
                    messageNotify("Click1",v,null);
                    try {

                        final View vF = v;
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
                       //cliente.send("Hola mundo!");
                        WebSocketClient client = null;
                        try {
                            client = new WebsocketClientEndpoint(new URI(destUri), new Draft_10());
                        } catch (Exception e) {
                            e.printStackTrace();
                            messageNotify("error",v,e);
                        }
                        client.connect();
                    }
                    catch (Exception ex) {

                        messageNotify("Error click 1",v,ex);
                    }

                }
            });

            Button button2 = (Button) findViewById(R.id.button2);
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    try
                    {
                        final View vF = v;

                        TextView textView = (TextView)findViewById(R.id.messagesServer);
                        textView.setText("");

                        TextView textView2 = (TextView)findViewById(R.id.messages);
                        textView2.setText("");

                        messageNotify("Click2", v, null);
                        connectWebSocket();
                    }
                    catch (Exception e)
                    {
                        messageNotify("Error CLick 2",v,e);
                    }

                }
            });


            connectWebSocket();

            //lv = (ListView) findViewById(R.id.listView1);
            //ListElementsArrayList = new ArrayList<String>
                    //(Arrays.asList(ListElements));

            //adapter = new ArrayAdapter<String>
                    //(MainActivity.this, android.R.layout.simple_list_item_1,
                            //ListElementsArrayList);

            //lv.setAdapter(adapter);
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
        try {

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

        TextView textView = (TextView)findViewById(R.id.messagesServer);
        textView.setText(textView.getText() + ".\n" +message);

        //ListView lv = (ListView)findViewById(R.id.listView1);

            //ListElementsArrayList.add(message);
            //adapter = new ArrayAdapter<String>
                    //(MainActivity.this, android.R.layout.simple_list_item_1,
                            //ListElementsArrayList);
            //lv.setAdapter(adapter);
        }
        catch (Exception e)
        {
            if(e!=null)
            {
                message = Log.getStackTraceString(exG);
            }

            Log.e(TAG, message);
            Log.println(1,TAG,message);
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

    private void connectWebSocket() {
        URI uri;
        concatUriCustomServer();

        try {
            uri = new URI(destUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        InetAddress vAdd = MyApplication.getInetAddress();
        final String strAdd = vAdd.getHostAddress().toString();

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Open WebsocketClient-->",destUri);
                String messageToSend = "Hello from " + Build.MANUFACTURER + " " + Build.MODEL;

                TextView usrT = (TextView)findViewById(R.id.userText);
                TextView pwdT = (TextView)findViewById(R.id.passwordText);

                messageToSend += "|<User>"+usrT.getText()+"</User><Pwd>"+pwdT.getText()+"</Pwd>";
                messageToSend += "<ip>"+strAdd+"</ip>";

                Log.e("WebsocketClient", "Message to Send-->[" + messageToSend + "]");

                messageNotify("WebsocketClient,Message to Send-->["+messageToSend+"]",null,null);

                mWebSocketClient.send(messageToSend);
                Log.e("WebsocketClient","Message Sent Ok!");
                messageNotify("WebsocketClient,Message Sent Ok!",null,null);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        TextView textView = (TextView)findViewById(R.id.messages);
                        textView.setText(textView.getText() + "\n" + message);
                        //Log.e("WebsocketClient", "MessageSent-->[" + message+"]");
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.e("WebsocketClient", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.e("WebsocketClient", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    private void concatUriCustomServer(){
        TextView serverIp = (TextView)findViewById(R.id.serverIpTxt);
        TextView serverPort = (TextView)findViewById(R.id.serverPortText);
        destUri = "ws://"+serverIp.getText()+":"+serverPort.getText()+"/";
        messageNotify("destUri-->" + destUri,null,null);
    }
}
