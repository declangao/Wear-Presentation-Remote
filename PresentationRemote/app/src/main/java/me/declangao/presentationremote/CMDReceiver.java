package me.declangao.presentationremote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Broadcast Receiver to execute commands without running the app
 */
public class CMDReceiver extends BroadcastReceiver {
    private static final String TAG = "CMDReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        // Get Shared Preferences
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        // Get command from bundle
        final String cmd = intent.getExtras().getString("cmd");
        Log.d(TAG, "CMD is: " + cmd);

        // Construct proper URL
        if (pref != null || !"".equals(pref.getString("url", ""))) {
            String url = pref.getString("url", "");
            if ("next".equals(cmd)) {
                url += "?key=next";
            } else {
                url += "?key=back";
            }
            Log.d(TAG, "URL is: " + url);

            StringRequest request = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, cmd.toUpperCase() + " command sent!");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Oops... Connection lost!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            Volley.newRequestQueue(context).add(request);
        }
    }
}
