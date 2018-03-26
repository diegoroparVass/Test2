package emermedica.test2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import emermedica.test2.utils.UtilsTab;

public class MainActivity extends Activity {

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

        InetAddress vAdd = UtilsTab.getLocalAddress();
        String strAdd = vAdd.getHostAddress().toString();

        serverIp.setText(strAdd);
        serverPort.setText("12345");

        messageNotify("IPAddresInfo Local - " + strAdd, null, null);
        concatUriCustomServer();

        try {

            Button button = (Button) findViewById(R.id.button1);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //onClick(v);
                    bnt1Event(v);
                }
            });

            Button button2 = (Button) findViewById(R.id.button2);
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    btn2Event(v);
                }
            });
            connectWebSocket();

            Button button3 = (Button) findViewById(R.id.btn3);
            button3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    btn3Event(v,"MainActivity2");
                }
                });

            Button button4 = (Button) findViewById(R.id.btn4);
            button4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    btn3Event(v,"MainActivity3");
                }
            });

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

    private void connectWebSocket() {
        URI uri;
        concatUriCustomServer();

        try {
            uri = new URI(destUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        InetAddress vAdd = UtilsTab.getLocalAddress();
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

    private void bnt1Event(View v)
    {
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

    private void btn2Event(View v)
    {
        messageNotify("Click2",v,null);
        try
        {
            final View vF = v;

            TextView textView = (TextView)findViewById(R.id.messagesServer);
            textView.setText("");

            TextView textView2 = (TextView)findViewById(R.id.messages);
            textView2.setText("");

            connectWebSocket();
        }
        catch (Exception e)
        {
            messageNotify("Error CLick 2",v,e);
        }
    }

    private void btn3Event(View v,String activityName) {

        messageNotify("Click3_"+activityName, v, null);
        try {

            PackageManager pacList = getPackageManager();

            String packName = "emermedica.test2";

            //Intent intent = getPackageManager().getLaunchIntentForPackage(packName);
            Intent intent2 = getPackageManager().getLaunchIntentForPackage(packName+"."+activityName);
            Intent intent = new Intent(Intent.ACTION_MAIN, null);

            //Intent intent = new Intent(Intent.ACTION_MAIN);
            //startActivity(intent);
            TextView userName = (TextView)findViewById(R.id.userText);
            TextView userPwd = (TextView)findViewById(R.id.passwordText);

            if (intent != null) {
                //intent.setComponent(new ComponentName(packName,packName+".MainActivity2"));
                // We found the activity now start the activity
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                final ComponentName cn = new ComponentName(packName, packName+"."+activityName);
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("userName",userName.getText());
                intent.putExtra("userPassword",userPwd.getText());

                startActivity(intent);
            } else {
                // Bring user to the market or let them choose an app?
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("market://details?id=" + packName));
                startActivity(intent);
            }

        } catch (Exception e) {
            messageNotify("Error CLick 3", v, e);
        }
    }

    public void startNewActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            // We found the activity now start the activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + packageName));
            context.startActivity(intent);
        }
    }
}
