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
import com.example.acquaint.realmdemo.model.JsonFruits;
import com.example.acquaint.realmdemo.model.UserModel;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

//public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> implements RealmChangeListener {
public class JsonListAdapter extends RealmRecyclerViewAdapter<JsonFruits, JsonListAdapter.ViewHolder> implements RealmChangeListener {

    private static final String TAG = JsonListAdapter.class.getSimpleName();
    private RealmResults<JsonFruits> mListOfUser;
    private Context mContext;
    private onItemClickListener mOnItemClickListener;

    public JsonListAdapter(@Nullable OrderedRealmCollection<JsonFruits> data, boolean autoUpdate,
                           Context mContext) {
        super(data, autoUpdate);
        this.mContext = mContext;

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
        final JsonFruits userModel = getItem(holder.getAdapterPosition());
        holder.txtFName.setText(String.format("%s %s", userModel.getFruit(), userModel.getColor()));
        holder.txtLName.setText(String.valueOf(userModel.getSize()));
        // holder.txtAge.setText(userModel.getUserAddress().getAddress());
//        holder.imEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mOnItemClickListener.onEditClickListener(userModel);
//            }
//        });
//        holder.imDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Realm realm = Realm.getDefaultInstance();
//                realm.executeTransaction(new Realm.Transaction() {
//                    @Override
//                    public void execute(@NonNull Realm realm) {
//                        userModel.deleteFromRealm();
//                    }
//                });
//            }
//        });
    }

    @Override
    public void onChange(@NonNull Object o) {
        Log.e(TAG, "onChange: " + o.toString());
        notifyDataSetChanged();
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
        void onEditClickListener(JsonFruits userModel);
    }
}
