package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.adapter.SearchListAdapter;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.CustomerModel;
import com.example.awizom.sanskritidecoreapp.model.DataOrder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SearchDetailsListActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;
    private ProgressDialog progressDialog;
    private List<DataOrder> searchList;
    private DataOrder dataOrderValue;
    private RecyclerView recyclerView;
    private SearchListAdapter adapter;
    ArrayAdapter<String> adapter1;
    private AutoCompleteTextView searchItem;
    private Button go;
    private String statusName = "";
    private String orderId = "";
    private String inputType = "";
    private long cid = 0;
    private List<CustomerModel> customerlist;
    private String[] customerNameList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details_list);
        initView();
    }

    private void initView() {

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search");
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
        searchItem = findViewById(R.id.searchItem);
        searchItem.setOnClickListener((View.OnClickListener) this);
        go = findViewById(R.id.goButton);
        go.setOnClickListener((View.OnClickListener) this);
        statusName = getIntent().getExtras().getString("StatusName", "");
        orderId = getIntent().getExtras().getString("OrderID", "");
        inputType = getIntent().getExtras().getString("InputTypeName", "");

//        getSupportActionBar().setTitle(statusName);
        dataOrderValue = new DataOrder();

        if (inputType.toString().equals("ByName")) {
            searchItem.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        } else if (inputType.toString().equals("ByNumber")) {
            searchItem.setInputType(InputType.TYPE_CLASS_NUMBER);
        }


        searchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (searchItem.getText().length() == 1) {
                    String.valueOf(cid);
                    //  searchItem.setText("");
                } else {
                    getCustomerDetail(searchItem.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSearchList();
            }
        });
        getCustomerDetailList();
    }

    /*customer get*/
    private void getCustomerDetailList() {
        try {
            new getCustomerList().execute(SharedPrefManager.getInstance(getApplicationContext()).getUser().access_token);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private class getCustomerList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CustomerGet/");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            }
            return json;
        }

        protected void onPostExecute(String result) {
            if (result.isEmpty()) {
                //  Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CustomerModel>>() {
                }.getType();
                customerlist = new Gson().fromJson(result, listType);
                customerNameList = new String[customerlist.size()];
                for (int i = 0; i < customerlist.size(); i++) {
                    if (inputType.equals("ByNumber"))
                        customerNameList[i] = String.valueOf(customerlist.get(i).getMobile());
                    else

                        customerNameList[i] = String.valueOf(customerlist.get(i).getCustomerName());

                }
                adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.select_dialog_item, customerNameList);
                searchItem.setThreshold(1);//will start working from first character
                searchItem.setAdapter(adapter1);//setting the adapter materialIdSelected into the AutoCompleteTextView
                //Getting the instance of AutoCompleteTextView
            }

        }
    }

    public void onClick(View v) {
        v.startAnimation(buttonClick);
        switch (v.getId()) {
            case R.id.goButton:

                if (!searchItem.getText().toString().isEmpty()) {
                    getSearchList();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill the field", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getSearchList() {
        try {
            mSwipeRefreshLayout.setRefreshing(true);
            new GetSearchDetails().execute(SharedPrefManager.getInstance(getApplicationContext()).getUser().access_token);
        } catch (Exception e) {
            e.printStackTrace();
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private class GetSearchDetails extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String accesstoken = params[0];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "OrderSearchGet/" + searchItem.getText().toString());
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
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
            if (result.isEmpty()) {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), "There is no materialIdSelected available" +
                        "", Toast.LENGTH_SHORT).show();
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<DataOrder>>() {
                }.getType();
                searchList = new Gson().fromJson(result, listType);
                adapter = new SearchListAdapter(SearchDetailsListActivity.this, searchList, statusName);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    private void getCustomerDetail(String cusname) {

        try {
            new getCustomer().execute(SharedPrefManager.getInstance(getApplicationContext()).getUser().access_token, cusname);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }

    }

    private class getCustomer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            String cusname = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CustomerGet/" + cusname);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            }
            return json;
        }

        protected void onPostExecute(String result) {

            try {

                if (result.isEmpty()) {
                    // Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<DataOrder>() {
                    }.getType();
                    dataOrderValue = new Gson().fromJson(result, listType);
                    if (dataOrderValue != null) {
                        cid = dataOrderValue.getCustomerID();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    ////////
}
