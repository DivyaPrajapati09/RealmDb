package com.example.acquaint.realmdemo.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.acquaint.realmdemo.R;
import com.example.acquaint.realmdemo.model.JsonFruits;
import com.example.acquaint.realmdemo.model.UserAddressModel;
import com.example.acquaint.realmdemo.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private EditText etFirstName, etLastName, etAge, etAddress;
    private Button btnSave, btnReadJson, btnReadJsonArray;
    private RecyclerView mRecyclerView;
    private String TAG = MainActivity.class.getSimpleName();
    private UserModel mUserModel;
    private ImageView imDeleteAll, imFilter;
    private RealmResults<UserModel> realmList;
    private RealmResults<JsonFruits> jsonFruits;
    private RealmResults<UserModel> userModels;
    private RealmResults<JsonFruits> fruitsModel;
    private DataListAdapter dataListAdapter;
    private JsonListAdapter jsonListAdapter;

    private RealmChangeListener<Realm> mOnDataChangeListener = new RealmChangeListener<Realm>() {
        @Override
        public void onChange(@NonNull Realm realm) {
            Log.e(TAG, "onChange : list all " + realm.where(UserModel.class).findAll());
            Log.e(TAG, "onChange : filter" + realm.where(UserModel.class).greaterThanOrEqualTo("age", 20).findAll());
        }
    };

    private View.OnClickListener mOnBtnSaveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    int nextID;
                    if (mUserModel == null) {
                        // increment index
                        Number num = realm.where(UserModel.class).max("id");

                        if (num == null) {
                            nextID = 1;
                        } else {
                            nextID = num.intValue() + 1;
                        }

                    } else {
                        nextID = mUserModel.getId();
                    }
                    UserModel obj = new UserModel();
                    obj.setId(nextID);
                    obj.setFirstName(etFirstName.getText().toString().trim());
                    obj.setLastName(etLastName.getText().toString().trim());
                    obj.setAge(Integer.parseInt(etAge.getText().toString().trim()));

//                    RealmList<String> setPhoneNumber = new RealmList<>();
//                    setPhoneNumber.add("123");
//                    setPhoneNumber.add("234");
//                    setPhoneNumber.add("345");
//                    setPhoneNumber.add("456");

//                    UserAddressModel userAddressModel = new UserAddressModel();
//                    userAddressModel.setAddress(etAddress.getText().toString().trim());
//                    userAddressModel.setId(nextID);
//                    userAddressModel.setPhoneNumber(setPhoneNumber);
//                    obj.setUserAddress(userAddressModel);

                    realm.copyToRealmOrUpdate(obj);
                    realm.addChangeListener(mOnDataChangeListener);
                    mUserModel = null;
                    etFirstName.setText("");
                    etLastName.setText("");
                    etAge.setText("");
                    etAddress.setText("");

                }
            });
        }
    };
    private DataListAdapter.onItemClickListener mOnItemClickListener = new DataListAdapter.onItemClickListener() {
        @Override
        public void onEditClickListener(UserModel userModel) {
            mUserModel = userModel;
            etFirstName.setText(userModel.getFirstName());
            etLastName.setText(userModel.getLastName());
            etAge.setText(String.valueOf(userModel.getAge()));
            if (userModel.getUserAddress() != null) {
                etAddress.setText(String.valueOf(userModel.getUserAddress().getAddress()));
            }
        }

    };
    private View.OnClickListener mOnDeleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
//                    realm.delete(UserModel.class);
//                    realm.delete(JsonFruits.class);
                    realm.deleteAll();
                }
            });

        }
    };
    private RealmChangeListener<RealmResults<UserModel>> mOnListDataChangeListener = new RealmChangeListener<RealmResults<UserModel>>() {
        @Override
        public void onChange(RealmResults<UserModel> userModels) {
            Log.e(TAG, "onChange: " + userModels);
        }
    };

    private View.OnClickListener mOnFilterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    userModels = realm.where(UserModel.class).greaterThanOrEqualTo("age", 20).findAll();
                }
            });
            dataListAdapter.updateData(userModels);
        }
    };

    private View.OnClickListener mOnBtnReadJsonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createOrUpdateObjectFromJson(JsonFruits.class, getResources().openRawResource(R.raw.example_1));
                }
            });
            fruitsModel = realm.where(JsonFruits.class).findAll();
            jsonListAdapter.updateData(fruitsModel);
        }
    };
    private View.OnClickListener mOnBtnJsonArrayClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createOrUpdateAllFromJson(JsonFruits.class, getResources().openRawResource(R.raw.example_2));
                }
            });
            fruitsModel = realm.where(JsonFruits.class).findAll();
            jsonListAdapter.updateData(fruitsModel);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Realm.init(this);
        realm = Realm.getDefaultInstance();

        etFirstName = findViewById(R.id.txt_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etAge = findViewById(R.id.et_age);
        etAddress = findViewById(R.id.et_address);
        imFilter = findViewById(R.id.im_filter);
        imFilter.setOnClickListener(mOnFilterClickListener);
        imDeleteAll = findViewById(R.id.im_delete_all);
        imDeleteAll.setOnClickListener(mOnDeleteClickListener);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(mOnBtnSaveClickListener);
        btnReadJson = findViewById(R.id.btn_read_json);
        btnReadJson.setOnClickListener(mOnBtnReadJsonClickListener);

        btnReadJsonArray = findViewById(R.id.btn_json_array);
        btnReadJsonArray.setOnClickListener(mOnBtnJsonArrayClickListener);
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String operation = getIntent().getStringExtra("operation");
        switch (operation) {
            case "insert":
                etFirstName.setVisibility(View.VISIBLE);
                etLastName.setVisibility(View.VISIBLE);
                etAge.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                realmList = realm.where(UserModel.class).findAll();
                dataListAdapter = new DataListAdapter(realmList, true, MainActivity.this, mOnItemClickListener);
                mRecyclerView.setAdapter(dataListAdapter);
                break;
            case "update":
                etFirstName.setVisibility(View.VISIBLE);
                etLastName.setVisibility(View.VISIBLE);
                etAge.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                realmList = realm.where(UserModel.class).findAll();
                dataListAdapter = new DataListAdapter(realmList, true, MainActivity.this, mOnItemClickListener);
                mRecyclerView.setAdapter(dataListAdapter);
                break;
            case "delete":
                mRecyclerView.setVisibility(View.VISIBLE);
                realmList = realm.where(UserModel.class).findAll();
                dataListAdapter = new DataListAdapter(realmList, true, MainActivity.this, mOnItemClickListener);
                mRecyclerView.setAdapter(dataListAdapter);
                break;
            case "deleteAll":
                mRecyclerView.setVisibility(View.VISIBLE);
                imDeleteAll.setVisibility(View.VISIBLE);
                realmList = realm.where(UserModel.class).findAll();
                dataListAdapter = new DataListAdapter(realmList, true, MainActivity.this, mOnItemClickListener);
                mRecyclerView.setAdapter(dataListAdapter);
                break;
            case "filterAll":
                mRecyclerView.setVisibility(View.VISIBLE);
                imFilter.setVisibility(View.VISIBLE);
                realmList = realm.where(UserModel.class).findAll();
                dataListAdapter = new DataListAdapter(realmList, true, MainActivity.this, mOnItemClickListener);
                mRecyclerView.setAdapter(dataListAdapter);
                break;
            case "readJsonObject":
                btnReadJson.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mRecyclerView.setAdapter(null);
                jsonFruits = realm.where(JsonFruits.class).findAll();
                jsonListAdapter = new JsonListAdapter(jsonFruits, true, MainActivity.this);
                mRecyclerView.setAdapter(jsonListAdapter);
                break;
            case "readJsonArray":
                btnReadJsonArray.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mRecyclerView.setAdapter(null);
                jsonFruits = realm.where(JsonFruits.class).findAll();
                jsonListAdapter = new JsonListAdapter(jsonFruits, true, MainActivity.this);
                mRecyclerView.setAdapter(jsonListAdapter);
                break;
            case "addListInData":

                break;
            case "sort":

                break;

        }
//        realm.deleteAll();


//        realmList = realm.where(UserModel.class).findAll();
//        dataListAdapter = new DataListAdapter(realmList, true, MainActivity.this, mOnItemClickListener);
//        mRecyclerView.setAdapter(dataListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
