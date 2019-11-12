package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.adapter.OrderCustomerFollowUpAdapter;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.modelnew.OrderNewOne;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class CustomerfollowUpList extends AppCompatActivity {

    RecyclerView recyclerView;
    private String result = "";
    List<OrderNewOne> orderList;
    OrderCustomerFollowUpAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getcustomer_layout);
        initView();
    }

    private void initView() {

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Customer Followup List");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in, R.anim.slide_out);
                onBackPressed();
            }
        });
        toolbar.setSubtitleTextAppearance(getApplicationContext(), R.style.styleA);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.styleA);
        toolbar.setTitleTextColor(Color.WHITE);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getOrderList();
            }
        });
        getOrderList();
    }

    private void getOrderList() {
        try {
            mSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetCustomerFollowUpList().execute(SharedPrefManager.getInstance(CustomerfollowUpList.this).getUser().access_token).get();

            if (result != null) {
                mSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<OrderNewOne>>() {
                }.getType();
                orderList = new Gson().fromJson(result, listType);
                Log.d("Error", orderList.toString());
                adapter = new OrderCustomerFollowUpAdapter(this, orderList);
                recyclerView.setAdapter(adapter);

            }

        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}
