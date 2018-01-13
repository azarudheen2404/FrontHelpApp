package in.esquareinfo.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

import in.esquareinfo.web.app.District;
import in.esquareinfo.web.app.HeaderCertificate;
import in.esquareinfo.web.app.State;

public class DashBoard extends NavigationActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public TextView totalAppl,totalAmtReq,approved,rejected,totalAmtAppr,totalAmtPaid;
    public TextView newRen,verPen,intPen,appRejPen,ins1Pen,ins2Pen;
    public String totalApplication, totalAmountReq,appApproved,appRejected,amtApproved,amtPaid;
    public String newApp,renApp,verificationPen,interviewPending,appRejPending,ins1Paid,ins1Pending,ins2Paid,ins2Pending;

    private String countByCourse,courseCount,courseName,disdata,distId,districtDet;

    private Spinner districtSpinner,stateSpinner,spinnerYear;
    private List<District> districtlist;
    private ArrayAdapter<District> dt;
    private TextView female,male;
    private LinearLayout fulData;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private List<State> methodlist;
    private ImageView search;
    private String name,paidCourse,paidCourseCount,paidCourseName,reqAmount,slctyear,spindist,spinstt;
    private ArrayAdapter<State> st;
    private String state,stateAppCount,stateDet,stateId,stateIdDis,stdata,txtFemale,txtMale,week,weekAppCount,weekDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dash_board);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_dash_board, contentFrameLayout);
        getSupportActionBar().setTitle("Dashboard");

        HeaderCertificate myHeaderClass = new HeaderCertificate(this);
        myHeaderClass.handleSSLHandshake();
        initObjects();
        initCallbacks();
        stdtdetails();
        initSpinners();
        fulData.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        dashboardData();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        if (adapterView == this.stateSpinner) {
            stdata = String.valueOf(methodlist.get(stateSpinner.getSelectedItemPosition()).getId());
            SharedPreferences pref = getApplicationContext().getSharedPreferences("pfijwt", MODE_PRIVATE);
            String stdt =pref.getString("StDt",null);

            districtlist.clear();
            JSONObject att = null;
            try {
                att= new JSONObject(stdt.toString().trim());
                String distlist = att.getString("districtList");
                Log.d("District", distlist.toString().trim());
                JSONArray district = new JSONArray(distlist.toString());
                for (int i = 0; i < district.length(); i++) {
                    JSONObject districtdata = district.getJSONObject(i);
                    String districtDet = districtdata.getString("name");
                    String distId = districtdata.getString("ID");
                    String stateIdDis = districtdata.getString("stateID");
                    if (stateIdDis.equals(stdata) || stateIdDis.equals("-1")) {
                        Log.d("Districttttttt", districtDet);
                        districtlist.add(new District(distId, districtDet));
                    }
                    dt.notifyDataSetChanged();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (adapterView == districtSpinner) {
            disdata = (districtlist.get(districtSpinner.getSelectedItemPosition())).getDisId();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void initObjects(){

        mContext = this;
        mProgressDialog = new ProgressDialog(mContext);
        stateSpinner = (Spinner) findViewById(R.id.spinnerState);
        districtSpinner = (Spinner) findViewById(R.id.spinnerDist);
        search = (ImageView) findViewById(R.id.search);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        methodlist = new ArrayList();
        districtlist = new ArrayList();
        st = new ArrayAdapter(mContext, R.layout.spinner_item, methodlist);
        dt = new ArrayAdapter(mContext, R.layout.spinner_item, districtlist);
        fulData = (LinearLayout) findViewById(R.id.fullDataLayout);
        totalAppl = (TextView) findViewById(R.id.toatalAppVal);
        totalAmtReq = (TextView) findViewById(R.id.totalAmtReq);
        approved = (TextView) findViewById(R.id.approved);
        rejected = (TextView) findViewById(R.id.rejected);
        totalAmtAppr = (TextView) findViewById(R.id.totalAmtApproved);
        totalAmtPaid = (TextView) findViewById(R.id.totalAmtPaid);
        newRen = (TextView) findViewById(R.id.new_renApp);
        verPen = (TextView) findViewById(R.id.verPending);
        intPen = (TextView) findViewById(R.id.intPending);
        appRejPen = (TextView) findViewById(R.id.app_rejPending);
        ins1Pen = (TextView) findViewById(R.id.ins1);
        ins2Pen = (TextView) findViewById(R.id.ins2);
        male = (TextView) findViewById(R.id.totalMale);
        female = (TextView) findViewById(R.id.totalFemale);
    }

    private void initCallbacks() {
        search.setOnClickListener(this);
        stateSpinner.setOnItemSelectedListener(this);
        districtSpinner.setOnItemSelectedListener(this);
    }

    private void initSpinners() {
        st.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(st);
        dt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(dt);
    }


    private void stdtdetails() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pfijwt", MODE_PRIVATE);
        String stdt =pref.getString("StDt",null);
        JSONObject att = null;
            try {
                att = new JSONObject(stdt.toString().trim());
                String statelist = att.getString("stateList");
                Log.d("State", statelist.toString().trim());
                JSONArray state = new JSONArray(statelist.toString());
                for (int i = 0; i < state.length(); i++) {
                    JSONObject statedata = state.getJSONObject(i);
                    stateDet = statedata.getString("name");
                    methodlist.add(new State(Integer.parseInt(statedata.getString("ID")), stateDet));
                    st.notifyDataSetChanged();
                }
                String distlist = att.getString("districtList");
                Log.d("District", distlist.toString().trim());
                JSONArray district = new JSONArray(distlist.toString());
                for (int j = 0; j < district.length(); j++) {
                    JSONObject districtdata = district.getJSONObject(j);
                    districtDet = districtdata.getString("name");
                    distId = districtdata.getString("ID");
                    stateIdDis = districtdata.getString("stateID");
                    districtlist.add(new District(distId, districtDet));
                    dt.notifyDataSetChanged();
                }
                List<String> yearlist = new ArrayList<String>();

                for (int i=2010;i<=2050;i++){
                    yearlist.add(String.valueOf(i));
                    ArrayAdapter yr = new ArrayAdapter(DashBoard.this,R.layout.spinner_item,yearlist);
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



    private void dashboardData(){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("pfijwt", MODE_PRIVATE);
        final String token = pref.getString("auth_token", null);

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, "https://schp.popularfrontindia.org/scholarshipQA/services/user/dashboard/getData?St="+stdata+"&Dt="+disdata+"&Yr="+slctyear,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response.toString());

                        JSONObject att = null;
                        try {
                            att = new JSONObject(response.toString().trim());
                            JSONObject dashData = att.getJSONObject("dashboardView");

                            totalApplication =dashData.getString("TotalApplications");
                            totalAmountReq=dashData.getString("TotalAmountRequired");
                            appApproved =dashData.getString("TotalApproved");
                            appRejected=dashData.getString("TotalRejected");
                            amtApproved =dashData.getString("TotalApprovedAmount");
                            amtPaid=dashData.getString("TotalAmountPaid");

                            //Second Data's

                            newApp=dashData.getString("TotalNewApplications");
                            renApp=dashData.getString("TotalRenewalApplications");
                            verificationPen=dashData.getString("TotalVerficationPending");
                            interviewPending=dashData.getString("TotalVerificationDone");
                            appRejPending=dashData.getString("TotalInterviewDone");
                            ins1Paid=dashData.getString("TotalFirstInstallmentDone");
                            ins1Pending=dashData.getString("TotalFirstInstallmentPending");
                            ins2Paid=dashData.getString("TotalSecondInstallmentDone");
                            ins2Pending=dashData.getString("TotalSecondInstallmentPending");

                            //first data

                            totalAppl.setText(totalApplication);
                            totalAmtReq.setText("\u20B9 "+totalAmountReq);
                            approved.setText(appApproved);
                            rejected.setText(appRejected);
                            totalAmtAppr.setText("\u20B9 "+amtApproved);
                            totalAmtPaid.setText("\u20B9 "+amtPaid);

                            //second data
                            newRen.setText(newApp+"/"+renApp);
                            verPen.setText(verificationPen);
                            intPen.setText(interviewPending);
                            appRejPen.setText(appRejPending);
                            ins1Pen.setText(ins1Paid+"/"+ins1Pending);
                            ins2Pen.setText(ins2Paid+"/"+ins2Pending);
                            fulData.setVisibility(View.VISIBLE);

                            state = dashData.getString("Statistics");
                            stateAppCountDetails();
                            countByCourse = dashData.getString("ApplicationCountByCourse");
                            courseCountDetails();
                            paidCourse = dashData.getString("PaidCountByCourse");
                            paidcourseCountDetails();
                            week = dashData.getString("ApplicationsPerWeek");
                            weekCountDetails();
                            txtMale = dashData.getString("TotalMale");
                            male.setText("Male: " + DashBoard.this.txtMale);
                            txtFemale = dashData.getString("TotalFemale");
                            female.setText("Female: " + DashBoard.this.txtFemale);



                        } catch (JSONException e) {
                            e.printStackTrace();
           /* Intent nostation = new Intent(UserSearchBus.this, Selection.class);
            startActivity(nostation);*/
                        }
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
                        Toast.makeText(DashBoard.this, "Not a time", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                header.put("pfijwt", token);
                return header;
            }
        };
        Log.d("URLLLLLL",stringRequest.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    private void stateAppCountDetails(){

        try {
            JSONArray rcview = new JSONArray(this.state.trim());
            TableLayout stk = (TableLayout) findViewById(R.id.table_state);
            stk.removeAllViewsInLayout();
            TableRow tbrow0 = new TableRow(this);
            tbrow0.setBackgroundResource(R.drawable.cell_shape);
            TextView textView1 = new TextView(this);
            textView1.setBackgroundResource(R.drawable.cell_shape);
            textView1.setText(" State ");
            textView1.setTextColor(Color.BLACK);
            textView1.setTypeface(Typeface.DEFAULT_BOLD);
            textView1.setTextSize(16);
            tbrow0.addView(textView1);
            TextView textView2 = new TextView(this);
            textView2.setBackgroundResource(R.drawable.cell_shape);
            textView2.setText(" Total Application ");
            textView2.setTextColor(Color.BLACK);
            textView2.setTextSize(16);
            textView2.setTypeface(Typeface.DEFAULT_BOLD);
            tbrow0.addView(textView2);
            stk.addView(tbrow0);
            TableLayout stk1 = (TableLayout) findViewById(R.id.table_req_amnt_state);
            stk1.removeAllViewsInLayout();
            TableRow tbrow0req = new TableRow(this);
            tbrow0req.setBackgroundResource(R.drawable.cell_shape);
            TextView text1View = new TextView(this);
            text1View.setBackgroundResource(R.drawable.cell_shape);
            text1View.setText(" State ");
            text1View.setTextColor(Color.BLACK);
            text1View.setTypeface(Typeface.DEFAULT_BOLD);
            text1View.setTextSize(16);
            tbrow0req.addView(text1View);
            TextView text2View = new TextView(this);
            text2View.setBackgroundResource(R.drawable.cell_shape);
            text2View.setText(" Required Amount ");
            text2View.setTextColor(Color.BLACK);
            text2View.setTextSize(16);
            text2View.setTypeface(Typeface.DEFAULT_BOLD);
            tbrow0req.addView(text2View);
            stk1.addView(tbrow0req);
            for (int i = 0; i < rcview.length(); i++) {
                JSONObject sview = rcview.getJSONObject(i);
                name = sview.getString("Name");
                stateAppCount = sview.getString("ApplicationCount");
                reqAmount = sview.getString("TotalAmount");
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setBackgroundResource(R.drawable.cell_shape);
                t1v.setText(name);
                t1v.setTextColor(Color.BLACK);
                t1v.setWidth(400);
                t1v.setHeight(100);
                if (i % 2 == 0) {
                    t1v.setBackgroundColor(Color.GRAY);
                } else {
                    t1v.setBackgroundColor(Color.WHITE);
                }
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setBackgroundResource(R.drawable.cell_shape);
                t2v.setText(stateAppCount);
                t2v.setTextColor(Color.BLACK);
                t2v.setHeight(100);
                t2v.setTypeface(Typeface.DEFAULT_BOLD);
                if (i % 2 == 0) {
                    t2v.setBackgroundColor(Color.GRAY);
                } else {
                    t2v.setBackgroundColor(Color.WHITE);
                }
                tbrow.addView(t2v);
                stk.addView(tbrow);
                TableRow tableRow1 = new TableRow(this);
                TextView t1vreq = new TextView(this);
                t1vreq.setBackgroundResource(R.drawable.cell_shape);
                t1vreq.setText(name);
                t1vreq.setTextColor(Color.BLACK);
                t1vreq.setWidth(400);
                t1vreq.setHeight(100);
                if (i % 2 == 0) {
                    t1vreq.setBackgroundColor(Color.GRAY);
                } else {
                    t1vreq.setBackgroundColor(Color.WHITE);
                }
                tableRow1.addView(t1vreq);
                TextView t2vreq = new TextView(this);
                t2vreq.setBackgroundResource(R.drawable.cell_shape);
                t2vreq.setText(reqAmount);
                t2vreq.setTextColor(Color.BLACK);
                t2vreq.setHeight(100);
                t2vreq.setTypeface(Typeface.DEFAULT_BOLD);
                if (i % 2 == 0) {
                    t2vreq.setBackgroundColor(Color.GRAY);
                } else {
                    t2vreq.setBackgroundColor(Color.WHITE);
                }
                tableRow1.addView(t2vreq);
                stk1.addView(tableRow1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

   private void courseCountDetails(){

       try {
           JSONArray rcview = new JSONArray(this.countByCourse.trim());
           TableLayout stk = (TableLayout) findViewById(R.id.table_no_app);
           stk.removeAllViewsInLayout();
           TableRow tbrow0 = new TableRow(this);
           tbrow0.setBackgroundResource(R.drawable.cell_shape);
           TextView tv0 = new TextView(this);
           tv0.setBackgroundResource(R.drawable.cell_shape);
           tv0.setText(" Course Name ");
           tv0.setTextColor(Color.BLACK);
           tv0.setTypeface(Typeface.DEFAULT_BOLD);
           tv0.setTextSize(16);
           tbrow0.addView(tv0);
           TextView tv1 = new TextView(this);
           tv1.setBackgroundResource(R.drawable.cell_shape);
           tv1.setText(" Total Application ");
           tv1.setTextColor(Color.BLACK);
           tv1.setTextSize(16);
           tv1.setTypeface(Typeface.DEFAULT_BOLD);
           tbrow0.addView(tv1);
           stk.addView(tbrow0);
           for (int i = 0; i < rcview.length(); i++) {
               JSONObject sview = rcview.getJSONObject(i);
               courseName = sview.getString("x");
               courseCount = sview.getString("y");
               TableRow tbrow = new TableRow(this);
               TextView t1v = new TextView(this);
               t1v.setBackgroundResource(R.drawable.cell_shape);
               t1v.setText(courseName);
               t1v.setTextColor(Color.BLACK);
               t1v.setWidth(400);
               t1v.setHeight(150);
               if (i % 2 == 0) {
                   t1v.setBackgroundColor(Color.GRAY);
               } else {
                   t1v.setBackgroundColor(Color.WHITE);
               }
               tbrow.addView(t1v);
               TextView t2v = new TextView(this);
               t2v.setBackgroundResource(R.drawable.cell_shape);
               t2v.setText(this.courseCount);
               t2v.setTextColor(Color.BLACK);
               t2v.setHeight(150);
               t2v.setTypeface(Typeface.DEFAULT_BOLD);
               if (i % 2 == 0) {
                   t2v.setBackgroundColor(Color.GRAY);
               } else {
                   t2v.setBackgroundColor(Color.WHITE);
               }
               tbrow.addView(t2v);
               stk.addView(tbrow);
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
   }

   private void paidcourseCountDetails(){
       try {
           JSONArray rcview = new JSONArray(this.paidCourse.trim());
           TableLayout stk = (TableLayout) findViewById(R.id.table_paid_course);
           stk.removeAllViewsInLayout();
           TableRow tbrow0 = new TableRow(this);
           tbrow0.setBackgroundResource(R.drawable.cell_shape);
           TextView tv0 = new TextView(this);
           tv0.setBackgroundResource(R.drawable.cell_shape);
           tv0.setText(" Course Name ");
           tv0.setTextColor(Color.BLACK);
           tv0.setTypeface(Typeface.DEFAULT_BOLD);
           tv0.setTextSize(16);
           tbrow0.addView(tv0);
           TextView tv1 = new TextView(this);
           tv1.setBackgroundResource(R.drawable.cell_shape);
           tv1.setText(" Total Application ");
           tv1.setTextColor(Color.BLACK);
           tv1.setTextSize(16);
           tv1.setTypeface(Typeface.DEFAULT_BOLD);
           tbrow0.addView(tv1);
           stk.addView(tbrow0);
           for (int i = 0; i < rcview.length(); i++) {
               JSONObject sview = rcview.getJSONObject(i);
               paidCourseName = sview.getString("x");
               paidCourseCount = sview.getString("y");
               TableRow tbrow = new TableRow(this);
               TextView t1v = new TextView(this);
               t1v.setBackgroundResource(R.drawable.cell_shape);
               t1v.setText(paidCourseName);
               t1v.setTextColor(Color.BLACK);
               t1v.setWidth(400);
               t1v.setHeight(150);
               if (i % 2 == 0) {
                   t1v.setBackgroundColor(Color.GRAY);
               } else {
                   t1v.setBackgroundColor(Color.WHITE);
               }
               tbrow.addView(t1v);
               TextView t2v = new TextView(this);
               t2v.setBackgroundResource(R.drawable.cell_shape);
               t2v.setText(paidCourseCount);
               t2v.setTextColor(Color.BLACK);
               t2v.setTypeface(Typeface.DEFAULT_BOLD);
               t2v.setHeight(150);
               if (i % 2 == 0) {
                   t2v.setBackgroundColor(Color.GRAY);
               } else {
                   t2v.setBackgroundColor(Color.WHITE);
               }
               tbrow.addView(t2v);
               stk.addView(tbrow);
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
   }

    private void weekCountDetails(){

        try {
            JSONArray rcview = new JSONArray(this.week.trim());
            TableLayout stk = (TableLayout) findViewById(R.id.table_week);
            stk.removeAllViewsInLayout();
            TableRow tbrow0 = new TableRow(this);
            tbrow0.setBackgroundResource(R.drawable.cell_shape);
            TextView tv0 = new TextView(this);
            tv0.setBackgroundResource(R.drawable.cell_shape);
            tv0.setText(" Week ");
            tv0.setTextColor(Color.BLACK);
            tv0.setTypeface(null, 1);
            tv0.setTextSize(16);
            tbrow0.addView(tv0);
            TextView tv1 = new TextView(this);
            tv1.setBackgroundResource(R.drawable.cell_shape);
            tv1.setText(" Total Application ");
            tv1.setTextColor(Color.BLACK);
            tv1.setTextSize(16);
            tv1.setTypeface(Typeface.DEFAULT_BOLD);
            tbrow0.addView(tv1);
            stk.addView(tbrow0);
            for (int i = 0; i < rcview.length(); i++) {
                JSONObject sview = rcview.getJSONObject(i);
                weekDetails = sview.getString("Label");
                weekAppCount = sview.getString("Count");
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setBackgroundResource(R.drawable.cell_shape);
                t1v.setText(weekDetails);
                t1v.setTextColor(Color.BLACK);
                t1v.setWidth(400);
                t1v.setHeight(150);
                if (i % 2 == 0) {
                    t1v.setBackgroundColor(Color.GRAY);
                } else {
                    t1v.setBackgroundColor(Color.WHITE);
                }
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setBackgroundResource(R.drawable.cell_shape);
                t2v.setText(this.weekAppCount);
                t2v.setTextColor(Color.BLACK);
                t2v.setTypeface(Typeface.DEFAULT_BOLD);
                if (i % 2 == 0) {
                    t2v.setBackgroundColor(Color.GRAY);
                } else {
                    t2v.setBackgroundColor(Color.WHITE);
                }
                t2v.setHeight(150);
                tbrow.addView(t2v);
                stk.addView(tbrow);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
