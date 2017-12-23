package in.esquareinfo.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.esquareinfo.web.app.District;
import in.esquareinfo.web.app.HeaderCertificate;

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
       // this.fulData.setVisibility(8);


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


    public void stdtdetails() {
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



    public void dashboardData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pfijwt", MODE_PRIVATE);
        String stdt =pref.getString("DashboardData",null);

        JSONObject att = null;
        try {
            att = new JSONObject(stdt.toString().trim());
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
          //  Log.d("DashData",dashData);

            /*JSONArray state = new JSONArray(statelist.toString());
            for (int i = 0; i < state.length(); i++) {
                JSONObject statedata = state.getJSONObject(i);
                stateDet = statedata.getString("name");
                stateId = statedata.getString("ID");
               *//* Log.d("State",stateDet.toString().trim());
                Log.d("StateID",stateId.toString().trim());*//*
            }*/

        } catch (JSONException e) {
            e.printStackTrace();
           /* Intent nostation = new Intent(UserSearchBus.this, Selection.class);
            startActivity(nostation);*/
        }

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
                        if (stateIdDis.equals(this.stdata) || stateIdDis.equals("-1")) {
                            Log.d("Districttttttt", districtDet);
                            this.districtlist.add(new District(distId, districtDet));
                        }
                        this.dt.notifyDataSetChanged();
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (adapterView == this.districtSpinner) {
            disdata = (districtlist.get(districtSpinner.getSelectedItemPosition())).getDisId();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
