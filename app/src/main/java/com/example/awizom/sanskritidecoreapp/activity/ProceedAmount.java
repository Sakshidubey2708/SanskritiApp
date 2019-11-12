package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.adapter.ProceedAmountAdapter;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.Result;
import com.example.awizom.sanskritidecoreapp.modelnew.CustomerOrderModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProceedAmount extends AppCompatActivity {

    RecyclerView recyclerView;
    private String result = "", orderid = "", orderitemId = "", a_id = "0",resulting="";
    ProceedAmountAdapter proceedAmountAdapter;
    List<CustomerOrderModel> customerOrderModelList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private AutoCompleteTextView advanceAmount;
    private TextView TotalAmount, PendingAmount;
    private long order_id = 0;
    private Button btn;
    ArrayList<String> mrps = new ArrayList<String>();
    ArrayList<String> catalogDiscount = new ArrayList<String>();
    ArrayList<String> AfterDiscountAmt = new ArrayList<String>();
    ArrayList<String> orderItem_id = new ArrayList<String>();
    ArrayList<String> ORderId = new ArrayList<String>();
    String totalPrice="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proceed_amount_activity);

        initView();
    }

    private void initView() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Cart");
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
        orderid = getIntent().getExtras().getString("ORDERID", "");
        //orderitemId = getIntent().getExtras().getString("ORDERITEMID", "");
        TotalAmount = findViewById(R.id.TotalAmount);
        advanceAmount = findViewById(R.id.advanceAmounts);
        PendingAmount = findViewById(R.id.pendingAmount);
        btn = findViewById(R.id.confirm);
        recyclerView = findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                GetCustomerOrderModel(orderid);
                GetAfterAmountOrder(orderid);
            }
        });

        GetCustomerOrderModel(orderid);
        GetAfterAmountOrder(orderid);


        advanceAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

               PendingAmount.setText(totalPrice);
                if (advanceAmount.getText().toString().length() == 0) {

                } else {
                    PendingAmount.setText("");
                    Double totalamount, advanceamt, pending, calculate, total;

                    totalamount = Double.parseDouble(TotalAmount.getText().toString());
                    advanceamt = Double.parseDouble(advanceAmount.getText().toString());
                    calculate = totalamount - advanceamt;
                    int i = Math.round(Float.parseFloat(calculate.toString()));
                    PendingAmount.setText(String.valueOf(i));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (advanceAmount.getText().toString().length() == 0) {

                } else {
                    Double totalamount, advanceamt, pending, calculate, total;

                    totalamount = Double.parseDouble(TotalAmount.getText().toString());
                    advanceamt = Double.parseDouble(advanceAmount.getText().toString());
                    calculate = totalamount - advanceamt;
                    int i = Math.round(Float.parseFloat(calculate.toString()));
                    PendingAmount.setText(String.valueOf(i));


                }

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    PostAmount();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void GetCustomerOrderModel(String oids) {
        try {
            mSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetAmountOrder().execute(oids.toString()).get();
            if (result.isEmpty()) {
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CustomerOrderModel>>() {
                }.getType();
                customerOrderModelList = new Gson().fromJson(result, listType);
                ProceedAmountAdapter proceedAmountAdapter = new ProceedAmountAdapter(this, customerOrderModelList);
                recyclerView.setAdapter(proceedAmountAdapter);
                Log.d("Error", customerOrderModelList.toString());




            }
        } catch (Exception e) {
            e.printStackTrace();
            mSwipeRefreshLayout.setRefreshing(false);

        }
    }

    private void GetAfterAmountOrder(String oids) {
        try {
            mSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetAfterAmountOrder().execute(oids.toString()).get();
            if (result.isEmpty()) {
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<CustomerOrderModel>() {
                }.getType();
                CustomerOrderModel dataOrderValue = new Gson().fromJson(result, listType);
                if (dataOrderValue != null) {
                  //  Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                    /*   TotalAmount.setText(result.toString());*/
                    totalPrice = String.valueOf(dataOrderValue.getMrp());
                    TotalAmount.setText(totalPrice);
                    PendingAmount.setText(totalPrice);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mSwipeRefreshLayout.setRefreshing(false);

        }
    }


    private void PostAmount() {
        try {

            try {
                result = new OrderHelper.PostAmount().execute("Cash", advanceAmount.getText().toString(), PendingAmount.getText().toString()
                        , TotalAmount.getText().toString(), String.valueOf(orderid), SharedPrefManager.getInstance(this).getUser().userID).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            try {
                if (result.isEmpty()) {
                    //Toast.makeText(OrderCreate.this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new Gson();

                    Result jsonbody = gson.fromJson(result, Result.class);
                    if (jsonbody.getStatus() == true) {
                        a_id = String.valueOf(jsonbody.getAmount_id());
                        Toast.makeText(ProceedAmount.this, String.valueOf(jsonbody.getOrderId()), Toast.LENGTH_SHORT).show();
                        try{
                            PostItemAmount(a_id);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(ProceedAmount.this, "Server Problem", Toast.LENGTH_SHORT).show();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void PostItemAmount(String a_id) {

        try {
            for (int i = 0; i < customerOrderModelList.size(); i++) {
                View view = recyclerView.getChildAt(i);

                TextView textView1 = view.findViewById(R.id.catalogMrps);
                String mrpTotal = textView1.getText().toString().split("/-")[0];
                mrps.add(mrpTotal);

                TextView textView2 = view.findViewById(R.id.catalog_Discount);
                String cataDiscount = textView2.getText().toString().split("%")[0];
                catalogDiscount.add(cataDiscount);

                TextView textView3 = view.findViewById(R.id.RESULTCALCULATEaFTERdISCOUNT);
                String Afterdiscount = textView3.getText().toString();
                AfterDiscountAmt.add(Afterdiscount);

                TextView textView4 = view.findViewById(R.id.order_item_id);
                String orderItemIds = textView4.getText().toString();
                orderItem_id.add(orderItemIds);

                TextView textView5 = view.findViewById(R.id.orderId);
                String ordrIds = textView5.getText().toString();
                ORderId.add(ordrIds);


            }
        }catch (Exception e){
            e.printStackTrace();
        }


        try {
            for (int i = 0; i < mrps.size(); i++) {
                try {
                    result = new OrderHelper.PostItemAmount().execute(a_id, mrps.get(i), catalogDiscount.get(i),AfterDiscountAmt.get(i), orderItem_id.get(i)).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                try {
                    if (result.isEmpty()) {
                        //Toast.makeText(OrderCreate.this, "Result empty", Toast.LENGTH_SHORT).show();
                    } else {
                        Gson gson = new Gson();

                        Result jsonbody = gson.fromJson(result, Result.class);
                        if (jsonbody.getStatus() == true) {
                            Toast.makeText(ProceedAmount.this, "Amount Confirm", Toast.LENGTH_SHORT).show();
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.BASE_URL + "Report/OrderDetail.aspx?OrderID=" + orderid));
                            startActivity(browserIntent);
                        } else {
                            Toast.makeText(ProceedAmount.this, "Server Problem", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
