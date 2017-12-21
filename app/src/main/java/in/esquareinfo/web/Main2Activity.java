package in.esquareinfo.web;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import in.esquareinfo.web.app.HeaderCertificate;

public class Main2Activity extends AppCompatActivity {

    public String param1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        HeaderCertificate myHeaderClass = new HeaderCertificate(this);
        myHeaderClass.handleSSLHandshake();

        //handleSSLHandshake();
        /*Uri data = this.getIntent().getData();
        Log.d("Data",data.toString());*/
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
      @Override
    protected void onStart() {
          super.onStart();
          Uri data = this.getIntent().getData();
         // Log.d("token",data.toString());
          param1 = data.getQueryParameter("token");
          Log.d("token",param1.toString());
          SharedPreferences pref = getApplicationContext().getSharedPreferences("pfijwt", MODE_PRIVATE);
          SharedPreferences.Editor editor = pref.edit();
          editor.putString("auth_token", param1.toString().trim());
          editor.apply();
          stDt();
        /*if (data != null && data.isHierarchical()) {
            if (data.getQueryParameter("token") != null) {

                // do some stuff
            }
        }*/
    }

    public void stDt(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pfijwt", MODE_PRIVATE);
        final String token = pref.getString("auth_token", null);
        StringRequest stringRequest1on;
        stringRequest1on = new StringRequest(Request.Method.GET, "https://schp.popularfrontindia.org/scholarshipQA/services/couser/lookup/getStDt",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("output",response.toString());
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("pfijwt", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("StDt",response.toString());
                        editor.commit();
                        Intent dash = new Intent(Main2Activity.this,SearchDetail.class);
                        startActivity(dash);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            Toast.makeText(Main2Activity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                        }
                        Log.d("Error",error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                header.put("pfijwt", token.toString().trim());
                return header;
            }
        };
        stringRequest1on.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
        requestQueue.add(stringRequest1on);
    }


}
