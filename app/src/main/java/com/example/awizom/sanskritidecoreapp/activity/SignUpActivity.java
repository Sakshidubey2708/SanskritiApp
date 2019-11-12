package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.helper.Helper;
import com.example.awizom.sanskritidecoreapp.model.UserRegister;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signupButton;
    private EditText userName, custContact, passWord, cnfrmPassWord;
    private TextView loginHere;
    private Intent intent;
    private ProgressDialog progressDialog;
    private String result = "";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initview();
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("SignUp");
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
    }

    private void initview() {
        userName = findViewById(R.id.userId);
        custContact = findViewById(R.id.custNames);
        passWord = findViewById(R.id.password);
        cnfrmPassWord = findViewById(R.id.cnfpassword);
        progressDialog = new ProgressDialog(this);

        signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(this);
        loginHere = findViewById(R.id.signInHere);
        loginHere.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(SignUpActivity.this);
            alertbox.setIcon(R.drawable.ic_warning_black_24dp);
            alertbox.setTitle("Do You Want To Exit Programme?");
            alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    // finish used for destroyed activity
                    finishAffinity();
                    System.exit(0);

                }
            });

            alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    // Nothing will be happened when clicked on no button
                    // of Dialog
                }
            });

            alertbox.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(buttonClick);
        switch (v.getId()) {
            case R.id.signupButton:

                if (userName.getText().toString().isEmpty() && custContact.getText().toString().isEmpty()
                        && passWord.getText().toString().isEmpty() && cnfrmPassWord.getText().toString().isEmpty()) {
                    userName.setError("Required");
                    custContact.setError("Required");
                    passWord.setError("Required");
                    cnfrmPassWord.setError("Required");
                } else if (!userName.getText().toString().isEmpty()) {


                    if (validation()) {
                        if (custContact.getText().toString().trim().length() < 10) {
                            custContact.setError("Required");
                        } else {
                            createUser();
                        }
                    }

                }

                break;
            case R.id.signInHere:
                startActivity(intent = new Intent(this, SignInActivity.class));
                break;
        }
    }

    private boolean validation() {

        String Username = userName.getText().toString();
        String UserMobilee = custContact.getText().toString();
        String UserPass = passWord.getText().toString();
        String UserCPass = cnfrmPassWord.getText().toString();
        boolean noSpecialChar = UserPass.matches("[a-zA-Z0-9 ]*");

        if (Username.trim().isEmpty()) {
            userName.setError("Required!");
        } else if (UserMobilee.toString().isEmpty()) {
            custContact.setError("Required!");
        } else if (UserPass.trim().isEmpty()) {
            passWord.setError("Required!");
        } else if (UserCPass.trim().isEmpty()) {
            cnfrmPassWord.setError("Required!");
        } else if (noSpecialChar) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "Password Pattern is Aa@123", Snackbar.LENGTH_LONG).show();
        } else if (!UserCPass.trim().contentEquals(UserPass.trim().toString())) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "Password Not Match", Snackbar.LENGTH_LONG).show();

        }
        return true;

    }

    private void createUser() {
        String name = userName.getText().toString().trim();
        String contact = userName.getText().toString().trim();
        String pwd = passWord.getText().toString().trim();
        String cpwd = cnfrmPassWord.getText().toString().trim();
        String ur = "User";
        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            result = new Helper.signUpHelper().execute(name, pwd, cpwd, ur).get();
            try {
                if (result.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    UserRegister.RootObject jsonbody = gson.fromJson(result, UserRegister.RootObject.class);
                    if (jsonbody.isStatus() == true) {

                        AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
                        alertDialog.setTitle("Registration successful !!");
                        alertDialog.setMessage("User is not active please contact your Admin");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startActivity(intent = new Intent(SignUpActivity.this, SignInActivity.class));
                                    }
                                });
                        alertDialog.show();
                    }
//                    else {
//                        Toast.makeText(SignUpActivity.this, "Some Problem here", Toast.LENGTH_SHORT).show();
//                    }

                }

            } catch (Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

}
