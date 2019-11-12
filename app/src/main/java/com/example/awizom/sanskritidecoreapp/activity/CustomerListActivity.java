package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.adapter.CustomerListAdapter;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.helper.Helper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.CustomerModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CustomerListActivity extends AppCompatActivity {

    private TextView customername, customeraddress, customercontact, interiorname, interiorcontact;
    private Intent intent;
    List<CustomerModel> orderList;
    RecyclerView recyclerView;
    CustomerListAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String result = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_item_layout);
        initView();
    }

    private void initView() {

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Customer List");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(CustomerListActivity.this, R.anim.slide_in, R.anim.slide_out);
                onBackPressed();
            }
        });
        toolbar.setSubtitleTextAppearance(CustomerListActivity.this, R.style.styleA);
        toolbar.setTitleTextAppearance(CustomerListActivity.this, R.style.styleA);
        toolbar.setTitleTextColor(Color.WHITE);

        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCustomerList();
            }
        });
        progressDialog = new ProgressDialog(this);

        getCustomerList();
    }

    private void getCustomerList() {
        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            mSwipeRefreshLayout.setRefreshing(true);
            result = new Helper.GetCustomerDetails().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {
                progressDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CustomerModel>>() {
                }.getType();
                progressDialog.dismiss();
                orderList = new Gson().fromJson(result, listType);
                adapter = new CustomerListAdapter(this, orderList);
                recyclerView.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }


}
