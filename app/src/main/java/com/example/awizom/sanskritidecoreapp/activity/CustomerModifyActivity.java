package com.example.awizom.sanskritidecoreapp.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.CustomerModel;
import com.example.awizom.sanskritidecoreapp.model.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class CustomerModifyActivity extends AppCompatActivity {

    Activity ctx=this;
    private EditText   cutContact,addreCust,custInterior,custIntcont;
    private AutoCompleteTextView custname;
    Button updatecust;
    LinearLayout SearchByNameLayout;
    private EditText  Custname, addreCusts,custInteriors,custIntconts;
    private AutoCompleteTextView custcontact;
    Button upcustbtn;
    LinearLayout SearchByNoLayout;
    TextView customerId,customerIds;
    private TextView searchByNameBtn,searchByNomberBtbn;
    String name, contact,address, interior, interiorContact, result="";
    private ProgressDialog progressDialog;
    private Intent intent;
    private List<CustomerModel> customerlist;
    private String[] customerNameList;
    private String[] customerMobileList;
    ArrayAdapter<String> adapter;
    private CustomerModel dataOrderValue;
    private long cid = 0;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_modify);
        initView();
    }


    private void initView() {

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Modify Customer");
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
        progressDialog = new ProgressDialog(this);
        getCustomerDetailList();
        getCustomerMobuileDetailList();

//Search By Name
        custname=findViewById(R.id.customerName);
        customerIds = findViewById(R.id.cIds);
        cutContact=findViewById(R.id.custContact);
        addreCust=findViewById(R.id.customerAddress);
        custInterior=findViewById(R.id.custInteriarName);
        custIntcont=findViewById(R.id.custInteriorMobile);
        updatecust=findViewById(R.id.updateCustButton);
        SearchByNameLayout = findViewById(R.id.SerchNaLayout);
        searchByNameBtn = findViewById(R.id.serchByNAme);
        searchByNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchByNoLayout.setVisibility(View.GONE);
                SearchByNameLayout.setVisibility(View.VISIBLE);
            }
        });


        custname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cutContact.setText("");
                addreCust.setText("");
                custInterior.setText("");
                custIntcont.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (custname.getText().length() > 0)
                    getCustomerName(custname.getText().toString());
//                getCustomerDetail();
            }
        });

//Search By Number
        Custname=findViewById(R.id.cName);
        customerId = findViewById(R.id.cId);
        custcontact=findViewById(R.id.customercontact);
        addreCusts=findViewById(R.id.cAddress);
        custInteriors=findViewById(R.id.cInteriarName);
        custIntconts=findViewById(R.id.cInteriorMobile);
        upcustbtn=findViewById(R.id.CustButton);
        SearchByNoLayout = findViewById(R.id.SearchNoLayout);
        searchByNomberBtbn = findViewById(R.id.searchByNo);
        searchByNomberBtbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchByNoLayout.setVisibility(View.VISIBLE);
                SearchByNameLayout.setVisibility(View.GONE);
            }
        });



        custcontact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Custname.setText("");
                addreCusts.setText("");
                custInteriors.setText("");
                custIntconts.setText("");

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (custcontact.getText().length() > 0)
                    getCustomerMobile(custcontact.getText().toString());
            }
        });




        upcustbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                if(validation()){
                    if(custcontact.getText().toString().trim().length() > 10 || custIntcont.getText().toString().trim().length() > 10
                            ){//|| custcontact.getText().toString().equals(custIntcont.getText().toString())
                        Toast.makeText(CustomerModifyActivity.this,"Contact no can't be same number",Toast.LENGTH_SHORT).show();
                    }else {
                        customerUpdatePostNo();
                    }
                }
            }
        });

        updatecust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                if(validation()){
                    if(custcontact.getText().toString().trim().length() > 10 || custIntcont.getText().toString().trim().length() > 10
                            ){//|| custcontact.getText().toString().equals(custIntcont.getText().toString())
                        Toast.makeText(CustomerModifyActivity.this,"Contact no can't be same number",Toast.LENGTH_SHORT).show();
                    }else {
                        customerUpdatePostName();
                    }
                }
            }
        });

    }

    private boolean validation() {

        String Username= custname.getText().toString();
        String UserMobilee= custcontact.getText().toString();
        String UserAddress= addreCust.getText().toString();



        if (Username.trim().isEmpty()) {
            custname.setError("Required!");

        } else if (UserMobilee.trim().isEmpty()) {
            custcontact.setError("Required!");

        } else if (UserAddress.trim().isEmpty()) {
            addreCust.setError("Required!");

        } else if (custcontact.getText().toString().equals(custIntcont.getText().toString())) {
            custcontact.setError("Required");
            custIntcont.setError("Required");

        }
        return true;

    }


    private void getCustomerDetail() {
        try {
            for (CustomerModel cm : customerlist) {
                if (cm.getCustomerName().equals(custname)) {
                    custcontact.setText(cm.getMobile());
                    addreCust.setText(cm.getAddress());
                    custInterior.setText(cm.getInteriorName());
                    custIntcont.setText(cm.getInteriorMobile());
                }
            }
        } catch (Exception e) {

        }

    }

    private void customerUpdatePostNo() {
        String customerIdc = customerIds.getText().toString().trim();
        String name = custname.getText().toString().trim();
        String contact = custcontact.getText().toString().trim();
        String address = addreCust.getText().toString().trim();
        String intename = custInterior.getText().toString().trim();
        String intecontact = custIntcont.getText().toString().trim();

        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            new POSTCustomer().execute(customerIdc,name, address, contact, intename, intecontact, SharedPrefManager.getInstance(this).getUser().access_token);
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();

        }
    }
    private void customerUpdatePostName() {
        String customerIdcs = customerId.getText().toString().trim();
        String name = custname.getText().toString().trim();
        String contact = custcontact.getText().toString().trim();
        String address = addreCust.getText().toString().trim();
        String intename = custInterior.getText().toString().trim();
        String intecontact = custIntconts.getText().toString().trim();

        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            new POSTCustomer().execute(customerIdcs,name, address, contact, intename, intecontact, SharedPrefManager.getInstance(this).getUser().access_token);
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();

        }
    }

    private class POSTCustomer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            //     InputStream inputStream

            String custId = params[0];
            String customername = params[1];
            String address = params[2];
            String mobile = params[3];
            String interiorname = params[4];
            String interiormobile = params[5];
            String accesstoken = params[6];

            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CustomerPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("CustomerID", custId);
                parameters.add("CustomerName", customername);
                parameters.add("Address", address);
                parameters.add("Mobile", mobile);
                parameters.add("InteriorName", interiorname);
                parameters.add("InteriorMobile", interiormobile);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();

                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
                // System.out.println("Error: " + e);

            }
            return json;
        }

        protected void onPostExecute(String result) {
            if (result.isEmpty()) {
                progressDialog.dismiss();

            } else {
                Gson gson = new Gson();
                final Result jsonbodyres = gson.fromJson(result, Result.class);
                Toast.makeText(CustomerModifyActivity.this, jsonbodyres.getMessage(), Toast.LENGTH_SHORT).show();

                if (jsonbodyres.getStatus() == true) {
                    intent = new Intent(CustomerModifyActivity.this,CustomerListActivity.class);
                    startActivity(intent);
                }
                progressDialog.dismiss();
            }
        }


    }


    private void getCustomerDetailList() {
        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            new getCustomerList().execute(SharedPrefManager.getInstance(this).getUser().access_token);
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private class getCustomerList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String accesstoken = strings[0];
            String json = "";
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
                progressDialog.dismiss();
                Toast.makeText(CustomerModifyActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
            return json;
        }

        protected void onPostExecute(String result) {
            if (result.isEmpty()) {
                progressDialog.dismiss();
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CustomerModel>>() {
                }.getType();
                customerlist = new Gson().fromJson(result, listType);
                customerNameList = new String[customerlist.size()];
                for (int i = 0; i < customerlist.size(); i++) {
                    customerNameList[i] = String.valueOf(customerlist.get(i).getCustomerName());
                }

                adapter = new ArrayAdapter<String>(CustomerModifyActivity.this, android.R.layout.select_dialog_item, customerNameList);
                custname.setThreshold(1);//will start working from first character
                custname.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                progressDialog.dismiss();


            }
        }
    }

    private void getCustomerMobuileDetailList() {
        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            new getCustomerMobuileDetailListList().execute(SharedPrefManager.getInstance(this).getUser().access_token);
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();

        }
    }

    private class getCustomerMobuileDetailListList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String accesstoken = strings[0];
            String json = "";
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
                progressDialog.dismiss();
            }
            return json;
        }

        protected void onPostExecute(String result) {
            if (result.isEmpty()) {
                progressDialog.dismiss();
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CustomerModel>>() {
                }.getType();
                customerlist = new Gson().fromJson(result, listType);
                customerMobileList = new String[customerlist.size()];
                for (int i = 0; i < customerlist.size(); i++) {
                    customerMobileList[i] = String.valueOf(customerlist.get(i).getMobile());
                }

                adapter = new ArrayAdapter<String>(CustomerModifyActivity.this, android.R.layout.select_dialog_item, customerMobileList);
                custcontact.setThreshold(1);//will start working from first character
                custcontact.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                progressDialog.dismiss();


            }
        }
    }

    private void getCustomerName(String cusname) {

        try {

            new getCustomer().execute(SharedPrefManager.getInstance(CustomerModifyActivity.this).getUser().access_token, cusname);
        } catch (Exception e) {
            e.printStackTrace();
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
            }
            return json;
        }

        protected void onPostExecute(String result) {
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<CustomerModel>() {
                }.getType();
                dataOrderValue = new Gson().fromJson(result, listType);
                if (dataOrderValue != null) {
                    cid = dataOrderValue.getCustomerID();
                    cutContact.setText(dataOrderValue.getMobile());
                    addreCust.setText(dataOrderValue.getAddress());
                    custInterior.setText(dataOrderValue.getInteriorName());
                    custIntcont.setText(dataOrderValue.getInteriorMobile());
                    customerId.setText(String.valueOf(dataOrderValue.getCustomerID()));
                }
            }
        }
    }

    private void getCustomerMobile(String cusname) {

        try {

            new getCustomerMobile().execute(SharedPrefManager.getInstance(CustomerModifyActivity.this).getUser().access_token, cusname);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class getCustomerMobile extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            String cusname = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CustomerGets/" + cusname);
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
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<CustomerModel>() {
                }.getType();
                dataOrderValue = new Gson().fromJson(result, listType);
                if (dataOrderValue != null) {
                    cid = dataOrderValue.getCustomerID();
                    Custname.setText(dataOrderValue.getCustomerName());
                    addreCusts.setText(dataOrderValue.getAddress());
                    custInteriors.setText(dataOrderValue.getInteriorName());
                    custIntconts.setText(dataOrderValue.getInteriorMobile());
                    customerIds.setText(String.valueOf(dataOrderValue.getCustomerID()));
                }
            }
        }
    }

}