package in.esquareinfo.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.esquareinfo.web.adapter.ViewAllApplicationsAdapter;
import in.esquareinfo.web.app.District;
import in.esquareinfo.web.app.HeaderCertificate;
import in.esquareinfo.web.app.State;
import in.esquareinfo.web.callback.ViewAllApplicationsCallback;
import in.esquareinfo.web.model.ViewAllApplicationsItem;

public class ViewAllApplicationActivity extends NavigationActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, ViewAllApplicationsCallback {

    private Context mContext;
    private ImageView search;
    private RecyclerView appRecyclerView;
    private ViewAllApplicationsAdapter applicationAdapter;
    private List<ViewAllApplicationsItem> applicationItemsList;
    private String address,appStatus,appTypeDesc,applicantData,applicantDetails,applicantName;
    private String course,dateOfBirth,disdata,distId,districtDet,genderDesc,slctyear,spindist;
    private String mobileNumber,profilId,profilrIdUrl,reqSchAmount,spinstt,stateDet,stateId,stateIdDis,stdata;
    private Spinner districtSpinner;
    private List<District> districtlist;
    private ArrayAdapter<District> dt;
    private Spinner stateSpinner;
    private List<State> methodlist;
    private ArrayAdapter<State> st;
    private Spinner spinnerYear;
    private LinearLayout fulData;
    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_view_all_application);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_dash_board, contentFrameLayout);
        getSupportActionBar().setTitle("All Applications");

        HeaderCertificate myHeaderClass = new HeaderCertificate(this);
        myHeaderClass.handleSSLHandshake();
        initObjects();
        initRecyclerView();
        initCallbacks();
        stdtdetails();
        initSpinners();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        appRecyclerView = (RecyclerView) findViewById(R.id.view_all_app_list);
        applicationItemsList = new ArrayList();
        applicationAdapter = new ViewAllApplicationsAdapter(mContext, applicationItemsList, this);
    }

    private void initRecyclerView() {
        appRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, 1, false));
        appRecyclerView.setAdapter(applicationAdapter);
    }

    private void initCallbacks(){
        st.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(st);
        dt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(dt);
    }

    private void stdtdetails(){
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
                ArrayAdapter yr = new ArrayAdapter(ViewAllApplicationActivity.this,R.layout.spinner_item,yearlist);
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

    private void initSpinners(){

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAllApplicationItemClick(int i) {

    }
}
