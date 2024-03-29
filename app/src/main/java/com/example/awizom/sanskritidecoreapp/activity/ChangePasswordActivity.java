package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.Result;
import com.google.gson.Gson;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText oldpassword, newPassword, cnfrmPassword;
    private Button changePasswordButton;
    private ProgressDialog progressDialog;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);
        initView();
    }

    private void initView() {

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Change Password");

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

        oldpassword = findViewById(R.id.oldPass);
        newPassword = findViewById(R.id.newPass);
        cnfrmPassword = findViewById(R.id.cnfrm_password);
        changePasswordButton = findViewById(R.id.changePassBtn);
        changePasswordButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(buttonClick);
        switch (v.getId()) {
            case R.id.changePassBtn:
                android.support.v7.app.AlertDialog.Builder alertbox = new android.support.v7.app.AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setTitle("Do you want to change the Password");
                alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        changePasswordMethodCall();

                    }
                });
                alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

                alertbox.show();

                break;
        }
    }

    private void changePasswordMethodCall() {

        if (validation()) {
            String oldPwd = oldpassword.getText().toString().trim();
            String newPwd = newPassword.getText().toString().trim();
            String cnPwd = cnfrmPassword.getText().toString().trim();
            try {
                progressDialog.setMessage("loading...");
                progressDialog.show();
                new changePasswordPost().execute(oldPwd, newPwd, cnPwd, SharedPrefManager.getInstance(getApplicationContext()).getUser().access_token);
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();

            }
        } else {
            Toast.makeText(getApplicationContext(), "Not Validate your fields ", Toast.LENGTH_SHORT).show();

        }
    }


    private boolean validation() {

        boolean status = true;
        if ((oldpassword.getText().toString().isEmpty())) {
            oldpassword.setError("Old name password is required!");
            status = false;
        } else if (newPassword.getText().toString().isEmpty()) {
            newPassword.setError("New password is required!");
            status = false;
        } else if (cnfrmPassword.getText().toString().isEmpty()) {
            cnfrmPassword.setError("Confirm password is required!");
            status = false;
        } else if (!newPassword.getText().toString().equals(cnfrmPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Password not match ", Toast.LENGTH_SHORT).show();
            status = false;
        }

        return status;

    }

    private class changePasswordPost extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String oldPwd = params[0];
            String newPwd = params[1];
            String cnPwd = params[2];
            String accesstoken = params[3];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API_REG + "ChangePassword");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("OldPassword", oldPwd);
                parameters.add("NewPassword", newPwd);
                parameters.add("ConfirmPassword", cnPwd);
                builder.post(parameters.build());
                builder.post(parameters.build());

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
                progressDialog.dismiss();
            } else {
                Gson gson = new Gson();
                final Result jsonbodyres = gson.fromJson(result, Result.class);
                Toast.makeText(getApplicationContext(), jsonbodyres.getMessage(), Toast.LENGTH_SHORT).show();
                if (jsonbodyres.getStatus() == true) {

                }
                progressDialog.dismiss();
            }
        }
    }
}
