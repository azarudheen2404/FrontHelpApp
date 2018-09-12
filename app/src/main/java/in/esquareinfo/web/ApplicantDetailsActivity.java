package in.esquareinfo.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import in.esquareinfo.web.app.District;
import in.esquareinfo.web.app.HeaderCertificate;
import in.esquareinfo.web.app.State;

public class ApplicantDetailsActivity extends AppCompatActivity {

    private Context mContext;
    private Toolbar toolbar;
    private TextView collegeAddress,collegeName,comments,currentCourse,dob,duration,email,guardianMobile,idProof;
    private TextView address,amountPaid,annualIncome;
    private TextView reqAmount,university,mobile,nameGender;
    private String appTypeDesc,academicDetails,applicantData;
    private String relationDetails,relationName,relationOccupation,relationshipType;
    private String txtAcademicCourse,txtAcademicInstitute,txtAcademicScore,txtAcademicScoreTotal,txtAcademicYear,txtAddress;
    private String txtAmountPaid,txtAnnualIncome,txtArea,txtCity,txtCollegeArea,txtCollegeDist,txtCollegeName,txtCollegeState;
    private String txtComments,txtCourse;
    private String txtCourseDuraton,txtDob,txtEmail,txtEndYear,txtGender,txtGuardianMobile,txtHouseNumber;
    private String txtIdName,txtIdProof,txtLandmark;
    private String txtMobile,txtName,txtPincode,txtProfile,txtReqAmount,txtRoadStreet,txtStartYear,txtUniversity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_applicant_details);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_dash_board, contentFrameLayout);
        getSupportActionBar().setTitle("Applicant Details");

        HeaderCertificate myHeaderClass = new HeaderCertificate(this);
        myHeaderClass.handleSSLHandshake();
        initObjects();
        initCallbacks();

       // fulData.setVisibility(View.GONE);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initObjects(){

    }

    private void initCallbacks(){

    }

    private void stdtdetails(){

    }

    private void initSpinners(){

    }

}
