package in.esquareinfo.web;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.esquareinfo.web.app.HeaderCertificate;

public class SearchDetail extends AppCompatActivity implements View.OnClickListener {

    public Spinner stateSpinner,districtSpinner,spinnerYear;
    public Button search;
    public String stateDet,districtDet,stateId,distId,spinstt,spindist,stdata,disdata,slctyear,stateIdDis;
    public NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        HeaderCertificate myHeaderClass = new HeaderCertificate(this);
        myHeaderClass.handleSSLHandshake();
        stateSpinner = (Spinner) findViewById(R.id.spinnerstate);
        districtSpinner = (Spinner) findViewById(R.id.spinnerdist);
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(this);

        spinnerYear=(Spinner)findViewById(R.id.spinnerYear);

        stdtdetails();
    }

    public void stdtdetails(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pfijwt", MODE_PRIVATE);
        String stdt =pref.getString("StDt",null);
        final List<String> methodlist = new ArrayList<String>();
        final List<String> districtlist = new ArrayList<String>();

        JSONObject att = null;
        try {
            att = new JSONObject(stdt.toString().trim());
            String statelist = att.getString("stateList");
            Log.d("State",statelist.toString().trim());
            JSONArray state = new JSONArray(statelist.toString());
            for (int i = 0; i < state.length(); i++) {
                JSONObject statedata = state.getJSONObject(i);
                stateDet = statedata.getString("name");
                stateId = statedata.getString("ID");
                SharedPreferences stdet = getApplicationContext().getSharedPreferences("stdet", MODE_PRIVATE);
                SharedPreferences.Editor editor = stdet.edit();
                editor.putString(stateDet, stateId);
                editor.commit();
                methodlist.add(stateDet);
                //Creating the ArrayAdapter instance having the bank name list
                ArrayAdapter st = new ArrayAdapter(SearchDetail.this,R.layout.spinner_item,methodlist);
                st.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                stateSpinner.setAdapter(st);
                stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> adapterView, View view,
                            int position, long l) {
                        SharedPreferences empdet = getApplicationContext().getSharedPreferences("stdet", MODE_PRIVATE);
                        spinstt = adapterView.getItemAtPosition(position).toString();
                        stdata = empdet.getString(spinstt,null);
                        Log.d("StateIdList",stdata);
                    }
                    public void onNothingSelected(
                            AdapterView<?> adapterView) {
                    }
                });
               /* Log.d("State",stateDet.toString().trim());
                Log.d("StateID",stateId.toString().trim());*/
            }

            String distlist = att.getString("districtList");
            Log.d("District",distlist.toString().trim());
            JSONArray district = new JSONArray(distlist.toString());
            for (int i = 0; i < district.length(); i++) {
                JSONObject districtdata = district.getJSONObject(i);
                districtDet = districtdata.getString("name");
                distId = districtdata.getString("ID");
                stateIdDis=districtdata.getString("stateID");
                SharedPreferences dtdet = getApplicationContext().getSharedPreferences("dtdet", MODE_PRIVATE);
                SharedPreferences.Editor editor = dtdet.edit();
                editor.putString(districtDet, distId);
                editor.commit();
                districtlist.add(districtDet);
                //Creating the ArrayAdapter instance having the bank name list
                ArrayAdapter dt = new ArrayAdapter(SearchDetail.this,R.layout.spinner_item,districtlist);
                dt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                districtSpinner.setAdapter(dt);
                districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> adapterView, View view,
                            int position, long l) {
                        SharedPreferences dtdet = getApplicationContext().getSharedPreferences("dtdet", MODE_PRIVATE);
                        spindist = adapterView.getItemAtPosition(position).toString();
                       // Log.d("DistIdList",spindist);
                        disdata = dtdet.getString(spindist,null);
                        Log.d("DistIdList",disdata);

                    }
                    public void onNothingSelected(
                            AdapterView<?> adapterView) {
                    }
                });
              /*  Log.d("District",districtDet.toString().trim());
                Log.d("DistrictId",distId.toString().trim());*/
            }

            List<String> yearlist = new ArrayList<String>();

            for (int i=2010;i<=2050;i++){
                yearlist.add(String.valueOf(i));
                ArrayAdapter yr = new ArrayAdapter(SearchDetail.this,R.layout.spinner_item,yearlist);
                yr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spinnerYear.setAdapter(yr);
                spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> adapterView, View view,
                            int position, long l) {
                        Log.d("YEAR",adapterView.getItemAtPosition(position).toString());
                        slctyear = adapterView.getItemAtPosition(position).toString();
                    }
                    public void onNothingSelected(
                            AdapterView<?> adapterView) {
                    }
                });
            }

      } catch (JSONException e) {
            e.printStackTrace();
           /* Intent nostation = new Intent(UserSearchBus.this, Selection.class);
            startActivity(nostation);*/
        }
    }

    public void profInfo() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("pfijwt", MODE_PRIVATE);
        final String token = pref.getString("auth_token", null);

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, "https://schp.popularfrontindia.org/scholarshipQA/services/user/dashboard/getData?St="+stdata+"&Dt="+disdata+"&Yr="+slctyear,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response.toString());

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("pfijwt", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("DashboardData",response.toString());
                        editor.commit();
                        Intent dash = new Intent(SearchDetail.this,DashBoard.class);
                        startActivity(dash);
                       /* JSONObject att = null;
                        try {
                            att = new JSONObject(response.trim());
                            image = att.getString("image");
                            uname = att.getString("name");
                            des = att.getString("designation");
                            String url = image + "/&token=" + token.toString().trim();
                            loadImageFromUrl(url);
                            userName.setText(uname);
                            desig.setText(des);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                      /*  if (networkResponse.statusCode==400){
                            Toast.makeText(Home.this,"Not a time", Toast.LENGTH_LONG).show();
                        }*/
                        Log.d("Error",error.toString());
                        Toast.makeText(SearchDetail.this, "Not a time", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                header.put("pfijwt", token);
                return header;
            }

           /* @Override
            protected Map<String, String> getParams ()throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("St", stdata);
                map.put("Dt", disdata);
                map.put("Yr", slctyear);
                return map;
            }*/
        };
        Log.d("URLLLLLL",stringRequest.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        profInfo();
    }
}
