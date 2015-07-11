package me.declangao.presentationremote;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private String baseURL;
    private static final int PORT = 5000;
    protected static final int NOTIF_ID = 12345;

    private SharedPreferences preferences;

    private EditText etIP;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnStart = (Button) findViewById(R.id.button_start);
        btnStart.setOnClickListener(this);
        etIP = (EditText) findViewById(R.id.editText);
        layout = (RelativeLayout) findViewById(R.id.layout);

        preferences = getSharedPreferences("pref", MODE_PRIVATE);
    }

    @Override
    public void onClick(final View v) {
        baseURL = "http://" + etIP.getText().toString().trim() + ":" + PORT + "/";
        String url = baseURL + "test";
        Log.d(TAG, "URL is: " + url);

        StringRequest testRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if ("200".equals(response)) {
                            Log.d(TAG, "Test connection succeeded!");
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.connection_succeeded_msg),
                                    Toast.LENGTH_LONG).show();
                            recordURL(); // Write URL to storage
                            showNotification();
                            finish(); // Exit
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Test connection failed...");
                //Toast.makeText(getApplicationContext(),
                //        "Connection failed. Please make sure you entered the right IP address and try again.",
                //        Toast.LENGTH_LONG).show();
                Snackbar.make(layout, getString(R.string.connection_failed_msg),
                        Snackbar.LENGTH_LONG).setAction(getString(R.string.retry),
                        MainActivity.this).show();
            }
        });
        Volley.newRequestQueue(this).add(testRequest);
    }

    /**
     * Show a notification with "Next" and "Back" actions so Wear devices can do something about it.
     */
    private void showNotification() {
        Intent nextIntent = new Intent("me.declangao.presentationremote.next");
        nextIntent.putExtra("cmd", "next");
        PendingIntent nextPendingIntent =
                PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent backIntent = new Intent("me.declangao.presentationremote.back");
        backIntent.putExtra("cmd", "back");
        PendingIntent backPendingIntent =
                PendingIntent.getBroadcast(this, 0, backIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(
                        MainActivity.this.getResources(), R.drawable.bg))
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notif_text))
                        //.setOngoing(true)
                .addAction(R.drawable.ic_action_next, getString(R.string.next), nextPendingIntent)
                .addAction(R.drawable.ic_action_back, getString(R.string.back), backPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIF_ID, builder.build());
    }

    /**
     * Write URL to SharedPreferences for storage
     */
    private void recordURL() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("url", baseURL);
        editor.commit();
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
    //    // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.menu_main, menu);
    //    return true;
    //}
    //
    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
    //    // Handle action bar item clicks here. The action bar will
    //    // automatically handle clicks on the Home/Up button, so long
    //    // as you specify a parent activity in AndroidManifest.xml.
    //    int id = item.getItemId();
    //
    //    //noinspection SimplifiableIfStatement
    //    if (id == R.id.action_settings) {
    //        return true;
    //    }
    //
    //    return super.onOptionsItemSelected(item);
    //}

}
