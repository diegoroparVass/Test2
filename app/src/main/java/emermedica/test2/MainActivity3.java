package emermedica.test2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import emermedica.test2.utils.UtilsTab;


public class MainActivity3 extends Activity {

    private String username="";
    private String passwordUser="";

    private static final String TAG = "MainActivity3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            if (savedInstanceState == null) {
                messageNotify("savedInstanceState is null!",null);
            }
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_activity3);

            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String strInfo = "<packageName>"+pinfo.packageName+"</packageName>";
            strInfo+= "<processName>"+pinfo.applicationInfo.processName+"</processName>";

            TextView titlePage = (TextView) findViewById(R.id.txtTitle);
            titlePage.setText(strInfo);

            TextView serverIp = (TextView) findViewById(R.id.act3txt1);
            TextView serverPort = (TextView) findViewById(R.id.act3txt2);

            Intent intent = this.getIntent();

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                username = extras.get("userName").toString();
                passwordUser = extras.get("userPassword").toString();

                messageNotify("username=" + username,null);
                messageNotify("Password=" + passwordUser,null);
            } else {
                messageNotify( "String Extras is null!",null);
            }

            serverIp.setText(username);
            serverPort.setText(passwordUser);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            messageNotify("Error",e);
        }
    }

   public void messageNotify(String message,Exception exG)
   {
       UtilsTab.messageNotify(message,exG,TAG,this);
   }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity3, menu);
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
}
