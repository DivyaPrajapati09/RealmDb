package com.example.acquaint.realmdemo.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acquaint.realmdemo.R;
import com.example.acquaint.realmdemo.model.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

//public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> implements RealmChangeListener {
public class DataListAdapter extends RealmRecyclerViewAdapter<UserModel, DataListAdapter.ViewHolder> implements RealmChangeListener, Filterable {

    private static final String TAG = DataListAdapter.class.getSimpleName();
    private RealmResults<UserModel> mListOfUser;
    private Context mContext;
    private onItemClickListener mOnItemClickListener;

    public DataListAdapter(@Nullable OrderedRealmCollection<UserModel> data, boolean autoUpdate,
                           Context mContext, onItemClickListener mOnItemClickListener) {
        super(data, autoUpdate);
        this.mContext = mContext;
        this.mOnItemClickListener = mOnItemClickListener;
    }

//    public DataListAdapter(RealmResults<UserModel> listOfUsers, Context context, onItemClickListener onItemClickListener) {
//        this.mListOfUser = listOfUsers;
//        mContext = context;
//        mListOfUser.addChangeListener(this);
//        mOnItemClickListener = onItemClickListener;
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final UserModel userModel = getItem(holder.getAdapterPosition());
        holder.txtFName.setText(String.format("%s %s", userModel.getFirstName(), userModel.getLastName()));
        holder.txtLName.setText(String.valueOf(userModel.getAge()));
        if (userModel.getUserAddress() != null) {
            RealmList<String> phoneNumber = userModel.getUserAddress().getPhoneNumber();
            List<String> listOfPhoneNumber = new ArrayList<>(phoneNumber);
            holder.txtAge.setText(String.format("%s\n%s", userModel.getUserAddress().getAddress(), android.text.TextUtils.join("\n", listOfPhoneNumber)));
            Log.e(TAG, "onBindViewHolder: " + phoneNumber);
        }
        holder.imEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onEditClickListener(userModel);
            }
        });
        holder.imDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(@NonNull Realm realm) {
                        userModel.deleteFromRealm();
                    }
                });
            }
        });
    }

    @Override
    public void onChange(@NonNull Object o) {
        Log.e(TAG, "onChange: " + o.toString());
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                Realm realm = Realm.getDefaultInstance();
                realm.where(UserModel.class).greaterThanOrEqualTo("age", 20).findAll();
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtFName, txtLName, txtAge;
        ImageView imEdit, imDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtFName = itemView.findViewById(R.id.txt_fname);
            txtLName = itemView.findViewById(R.id.txt_last_name);
            txtAge = itemView.findViewById(R.id.txt_age);
            imDelete = itemView.findViewById(R.id.im_delete);
            imEdit = itemView.findViewById(R.id.im_edit);
        }
    }

    public interface onItemClickListener {
        void onEditClickListener(UserModel userModel);
    }
}
