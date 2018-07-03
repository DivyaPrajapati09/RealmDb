package com.example.acquaint.realmdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.acquaint.realmdemo.R;

public class HomeActivity extends AppCompatActivity {

    private View.OnClickListener mOnInsertClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            intent.putExtra("operation","insert");
            startActivity(intent);
        }
    };
    private View.OnClickListener mOnUpdateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            intent.putExtra("operation","update");
            startActivity(intent);
        }
    };
    private View.OnClickListener mOnDeleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            intent.putExtra("operation","delete");
            startActivity(intent);
        }
    };
    private View.OnClickListener mOnDeleteAllClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            intent.putExtra("operation","deleteAll");
            startActivity(intent);
        }
    };
    private View.OnClickListener mOnFilterAllClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            intent.putExtra("operation","filterAll");
            startActivity(intent);
        }
    };
    private View.OnClickListener mOnBtnReadJsonObjectClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            intent.putExtra("operation","readJsonObject");
            startActivity(intent);
        }
    };
    private View.OnClickListener mOnBtnReadJsonArrayClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            intent.putExtra("operation","readJsonArray");
            startActivity(intent);
        }
    };
    private View.OnClickListener mOnAddListDataInDbClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            intent.putExtra("operation","addListInData");
            startActivity(intent);
        }
    };
    private View.OnClickListener mOnSortingClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            intent.putExtra("operation","sort");
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnInsert = findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(mOnInsertClickListener);

        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(mOnUpdateClickListener);

        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(mOnDeleteClickListener);

        Button btnDeleteAll = findViewById(R.id.btn_delete_all);
        btnDeleteAll.setOnClickListener(mOnDeleteAllClickListener);

        Button btnFilter = findViewById(R.id.btn_filter);
        btnFilter.setOnClickListener(mOnFilterAllClickListener);

        Button btnReadJsonObject = findViewById(R.id.btn_read_json_object);
        btnReadJsonObject.setOnClickListener(mOnBtnReadJsonObjectClickListener);

        Button btnReadJsonArray = findViewById(R.id.btn_read_json_array);
        btnReadJsonArray.setOnClickListener(mOnBtnReadJsonArrayClickListener);

        Button btnAddListOfDataInDb = findViewById(R.id.add_list_in_db);
        btnAddListOfDataInDb.setOnClickListener(mOnAddListDataInDbClickListener);

        Button btnSorting = findViewById(R.id.btn_sorting);
        btnSorting.setOnClickListener(mOnSortingClickListener);
    }
}
