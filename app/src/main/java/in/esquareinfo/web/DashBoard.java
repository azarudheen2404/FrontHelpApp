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
       /* stdtdetails();
        initSpinners();*/
       // this.fulData.setVisibility(8);


        dashboardData();

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

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}