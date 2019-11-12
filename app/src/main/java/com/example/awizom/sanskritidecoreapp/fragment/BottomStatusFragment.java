package com.example.awizom.sanskritidecoreapp.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.activity.CustomerfollowUpList;
import com.example.awizom.sanskritidecoreapp.activity.GetPendingAmountActivity;
import com.example.awizom.sanskritidecoreapp.activity.GetPendingOrderActivity;
import com.example.awizom.sanskritidecoreapp.activity.GetPendingTailorActivity;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.modelnew.StatusNewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class BottomStatusFragment extends Fragment implements View.OnClickListener {

    private TextView pendingTailor, pendngOrder, pendingAmount;
    private Intent intent;
    //check

      String[] values;
      SwipeRefreshLayout mSwipeRefreshLayout;
      ScrollView scrollView;
      StatusNewModel statusModel;
    private long pendingAmt = 0,pendingOrder=0,Pendingtailor=0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_status_layout, container, false);
        initView(view);

        return view;

    }
    private void initView(View view) {

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        scrollView=view.findViewById(R.id.scroll);

        try{
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    GetStatusCountMethodCall();


                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }




        pendingTailor = view.findViewById(R.id.pendingtTailorTextView);
        pendngOrder = view.findViewById(R.id.pendingOrder);
        pendingAmount = view.findViewById(R.id.pendingToAmount);

        pendingTailor.setOnClickListener(this);
        pendngOrder.setOnClickListener(this);
        pendingAmount.setOnClickListener(this);

        GetStatusCountMethodCall();

    }


    @Override
    public void onClick(View v) {
        Class fragmentClass = null;
        switch (v.getId()) {
            case R.id.pendingtTailorTextView:
                intent = new Intent(getContext(), GetPendingTailorActivity.class);
                startActivity(intent);
                break;
            case R.id.pendingOrder:
                intent = new Intent(getContext(), GetPendingOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.pendingToAmount:

                intent = new Intent(getContext(), GetPendingAmountActivity.class);
                startActivity(intent);
                break;

        }

    }


    private void GetStatusCountMethodCall() {

        try {
            mSwipeRefreshLayout.setRefreshing(true);
            new statusCountGET().execute();
        } catch (Exception e) {
            mSwipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();
            Toast.makeText(getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();

        }
    }

    private class statusCountGET extends AsyncTask<String, Void, String> implements View.OnClickListener {
        @Override
        protected String doInBackground(String... params) {
            mSwipeRefreshLayout.setRefreshing(false);
            String json = "";

            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetOrderStatusCount");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");


                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);

                } else {

                    Gson gson = new Gson();
                    Type listType = new TypeToken<StatusNewModel>() {
                    }.getType();
                    statusModel = new Gson().fromJson(result, listType);
                    if (statusModel != null) {
                        pendingOrder = statusModel.getPendingOrder();
                        pendingAmt = statusModel.getPendingAmount();
                        Pendingtailor = statusModel.getPendingTellor();
                        pendingTailor.setText("Pending Tailor List"+" ("+String.valueOf(Pendingtailor)+")");
                        pendngOrder.setText("Pending Order List"+" ("+String.valueOf(pendingOrder)+")");
                        pendingAmount.setText("Pending Amount List"+" ( "+String.valueOf(pendingAmt)+")");
                    }

                    mSwipeRefreshLayout.setRefreshing(false);

                }
            } catch (Exception e) {
                e.printStackTrace();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onClick(View v) {

        }
    }






}

