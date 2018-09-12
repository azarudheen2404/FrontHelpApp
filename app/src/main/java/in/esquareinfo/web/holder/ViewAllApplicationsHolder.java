package in.esquareinfo.web.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.esquareinfo.web.R;
import in.esquareinfo.web.callback.ViewAllApplicationsCallback;

public class ViewAllApplicationsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView address;
    private ViewAllApplicationsCallback allApplicationsCallback;
    private TextView amount_status;
    private TextView course;
    private TextView id_type;
    private TextView mob_dob;
    private TextView name_gender;

    public ViewAllApplicationsHolder(View itemView, ViewAllApplicationsCallback callback) {
        super(itemView);
        id_type = (TextView) itemView.findViewById(R.id.profId_appType);
        name_gender = (TextView) itemView.findViewById(R.id.name_gender);
        amount_status = (TextView) itemView.findViewById(R.id.reqAmount_appStatus);
        mob_dob = (TextView) itemView.findViewById(R.id.mob_dob);
        course = (TextView) itemView.findViewById(R.id.course);
        address = (TextView) itemView.findViewById(R.id.address);
        allApplicationsCallback = callback;
        itemView.setOnClickListener(this);
    }

    public TextView getIdType() {
        return this.id_type;
    }

    public TextView getNameGender() {
        return this.name_gender;
    }

    public TextView getAmountStatus() {
        return this.amount_status;
    }

    public TextView getMobDob() {
        return this.mob_dob;
    }

    public TextView getCourse() {
        return this.course;
    }

    public TextView getAddress() {
        return this.address;
    }

    public void onClick(View v) {
        if (v == this.itemView) {
            this.allApplicationsCallback.onAllApplicationItemClick(getAdapterPosition());
        }
    }
}
