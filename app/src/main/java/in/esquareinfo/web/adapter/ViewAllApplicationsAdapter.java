package in.esquareinfo.web.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import in.esquareinfo.web.R;
import in.esquareinfo.web.ViewAllApplicationActivity;
import in.esquareinfo.web.callback.ViewAllApplicationsCallback;
import in.esquareinfo.web.holder.ViewAllApplicationsHolder;
import in.esquareinfo.web.model.ViewAllApplicationsItem;

public class ViewAllApplicationsAdapter extends RecyclerView.Adapter<ViewAllApplicationsHolder> {

    private ViewAllApplicationsCallback allApplicationsCallbackCallback;
    private List<ViewAllApplicationsItem> mAllAppList;
    private Context mContext;

    public ViewAllApplicationsAdapter(Context context, List<ViewAllApplicationsItem> applicationsItemList,
                                      ViewAllApplicationsCallback callback) {
        mContext = context;
        mAllAppList = applicationsItemList;
        allApplicationsCallbackCallback = callback;
    }

    public ViewAllApplicationsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewAllApplicationsHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_applications_item, parent, false),allApplicationsCallbackCallback);
    }

    public void onBindViewHolder(ViewAllApplicationsHolder holder, int position) {
        ViewAllApplicationsItem applicationItem = mAllAppList.get(position);
        String id_type = applicationItem.getProfId();
        String name_gender = applicationItem.getApplicantName();
        String amount_status = applicationItem.getReqAmount();
        String mob_dob = applicationItem.getMobile();
        String course = applicationItem.getCourse();
        String address = applicationItem.getAddress();
        holder.getIdType().setText(id_type);
        holder.getNameGender().setText(name_gender);
        holder.getAmountStatus().setText(amount_status);
        holder.getMobDob().setText(mob_dob);
        holder.getCourse().setText(course);
        holder.getAddress().setText(address);
    }

    public int getItemCount() {
        return this.mAllAppList.size();
    }
}
