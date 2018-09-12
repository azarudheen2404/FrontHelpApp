package in.esquareinfo.web.model;

public class ViewAllApplicationsItem {

    private String address,applicantName_applicantgender,course,mobile_dateOfBirth,profId_appType,reqAmount_appStatus;

    public ViewAllApplicationsItem(String prof_type, String name_gender, String amount_status, String mob_dob,
                                   String crse, String addrs) {
        profId_appType = prof_type;
        applicantName_applicantgender = name_gender;
        reqAmount_appStatus = amount_status;
        mobile_dateOfBirth = mob_dob;
        course = crse;
        address = addrs;
    }

    public String getProfId() {
        return profId_appType;
    }

    public void setProfId(String profId) {
        profId_appType = profId;
    }

    public String getApplicantName() {
        return applicantName_applicantgender;
    }

    public void setApplicantName(String applicantName) {
        applicantName_applicantgender = applicantName;
    }

    public String getReqAmount() {
        return reqAmount_appStatus;
    }

    public void setReqAmount(String reqAmount) {
        reqAmount_appStatus = reqAmount;
    }

    public String getMobile() {
        return mobile_dateOfBirth;
    }

    public void setMobile(String mobile) {
        mobile_dateOfBirth = mobile;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        course = course;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = address;
    }
}
