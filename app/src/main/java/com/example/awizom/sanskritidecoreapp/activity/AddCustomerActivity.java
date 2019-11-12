package com.example.awizom.sanskritidecoreapp.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.Helper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.Result;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class AddCustomerActivity extends AppCompatActivity {

    private Activity ctx = this;
    private EditText custname, custcontact, addreCust, custInterior, custIntcont;
    private Button addcust;
    private String name, contact, address, interiorName, interiorContact;
    private ProgressDialog progressDialog;
    private String result = "";
    private Intent intent;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        custname = findViewById(R.id.custNames);
        custcontact = findViewById(R.id.customercontact);
        addreCust = findViewById(R.id.customerAddress);
        custInterior = findViewById(R.id.custInteriarName);
        custIntcont = findViewById(R.id.custInteriorMobile);
        addcust = findViewById(R.id.addCustButton);
        progressDialog = new ProgressDialog(this);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Customer");
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

        addcust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                switch (v.getId()) {
                    case R.id.addCustButton:
                        if (validation()) {
                            if (custcontact.getText().toString().trim().length() < 10 || addreCust.getText().toString().isEmpty()) {
                                addreCust.setError("Required !");
                                Toast.makeText(AddCustomerActivity.this, "Contact no can't be less then 10", Toast.LENGTH_SHORT).show();
                            } else if (custIntcont.getText().toString().trim().length() > 10) {
                                if (custIntcont.getText().toString().trim().length() < 10
                                        || custcontact.getText().toString().equals(custIntcont.getText().toString())) {
                                    Toast.makeText(AddCustomerActivity.this, "Contact no can't be same", Toast.LENGTH_SHORT).show();
                                } else {
                                    AddCustomer();
                                }
                            } else {

                                AddCustomer();
                            }
                        }
                        break;
                }
            }
        });

    }

    private boolean validation() {

        String Username = custname.getText().toString();
        String UserMobilee = custcontact.getText().toString();
        String UserAddress = addreCust.getText().toString();


        if (Username.trim().isEmpty()) {
            custname.setError("Required!");
        } else if (UserMobilee.trim().isEmpty()) {
            custcontact.setError("Required!");
        } else if (UserAddress.trim().isEmpty()) {
            addreCust.setError("Required!");
        }
//        else if (custcontact.getText().toString().equals(custIntcont.getText().toString())) {
//            custcontact.setError("Required");
//            custIntcont.setError("Required");
//        }
        return true;

    }

    private void customerAddPost() {

        name = custname.getText().toString();
        contact = custcontact.getText().toString();
        address = addreCust.getText().toString();
        interiorName = custInterior.getText().toString();
        interiorContact = custIntcont.getText().toString();
        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            result = new Helper.AddCustomer().execute(SharedPrefManager.getInstance(this).getUser().access_token,
                    name, contact, address, interiorName, interiorContact).get();
            try {
                if (result != null) {
                    progressDialog.dismiss();
                    Toast.makeText(AddCustomerActivity.this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    final Result jsonbodyres = gson.fromJson(result, Result.class);
                    Toast.makeText(AddCustomerActivity.this, jsonbodyres.getMessage(), Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddCustomerActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }

    }

    private void AddCustomer() {

        name = custname.getText().toString();
        contact = custcontact.getText().toString();
        address = addreCust.getText().toString();
        interiorName = custInterior.getText().toString();
        interiorContact = custIntcont.getText().toString();

        try {
            //String res="";
            progressDialog.setMessage("loading...");
            progressDialog.show();
            new POSTCustomerAdd().execute(name, contact, address, interiorName, interiorContact, SharedPrefManager.getInstance(AddCustomerActivity.this).getUser().access_token);
            try {
                if (result != null) {
                    progressDialog.dismiss();

                } else {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    final Result jsonbodyres = gson.fromJson(result, Result.class);
                    Toast.makeText(AddCustomerActivity.this, jsonbodyres.getMessage(), Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddCustomerActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }


    }

    private class POSTCustomerAdd extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            //     InputStream inputStream

            String customername = params[0];
            String mobile = params[1];
            String address = params[2];
            String interiorname = params[3];
            String interiormobile = params[4];
            String accesstoken = params[5];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CustomerPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("CustomerID", "0");
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
                progressDialog.dismiss();
                Gson gson = new Gson();
                final Result jsonbodyres = gson.fromJson(result, Result.class);

                if (jsonbodyres.getMessage().contains("Mobile already exist")) {
                    Toast.makeText(AddCustomerActivity.this, jsonbodyres.getMessage(), Toast.LENGTH_SHORT).show();

                } else {

                    Intent intent = new Intent(AddCustomerActivity.this, OrderCreate.class);
                    intent = intent.putExtra("CustomerName", name.toString());
                    intent = intent.putExtra("Mobile", contact.toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
//                    custname.setText("");
//                    custcontact.setText("");
//                    addreCust.setText("");
//                    custInterior.setText("");
//                    custIntcont.setText("");

                }

            }
        }
    }
}
