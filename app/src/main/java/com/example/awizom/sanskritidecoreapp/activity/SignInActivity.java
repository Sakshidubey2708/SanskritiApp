package com.example.awizom.sanskritidecoreapp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.helper.Helper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.Token;
import com.example.awizom.sanskritidecoreapp.model.UserLogin;
import com.google.gson.Gson;
//jdgjgfdhs

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {


    private Button signinButton;
    private EditText userName, passWord;
    private TextView signupHere;
    String result = "";
    ProgressDialog progressDialog;
    Intent intent;
    boolean connected = false;
    private static int TIMER = 300;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ActivityCompat.requestPermissions(SignInActivity.this,
                new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(SignInActivity.this);
            alertbox.setIcon(R.drawable.ic_warning_black_24dp);
            alertbox.setTitle("Do You Want To Exit Programme?");
            alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    finishAffinity();
                    System.exit(0);


                }
            });

            alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });

            alertbox.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        checkInternet();
        progressDialog = new ProgressDialog(this);
        userName = findViewById(R.id.userId);
        passWord = findViewById(R.id.password);
        signinButton = findViewById(R.id.signinButton);
        signupHere = findViewById(R.id.signupHere);
        signinButton.setOnClickListener(this);
        signupHere.setOnClickListener(this);

        try {
            if (!SharedPrefManager.getInstance(SignInActivity.this).getUser().access_token.equals(null)) {
                if (SharedPrefManager.getInstance(SignInActivity.this).getUser().userRole.contains("Admin")) {
                    Intent log = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(log);  overridePendingTransition(R.anim.slide_out, R.anim.slide_in);

                } else {
                    Intent log = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(log);  overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userLogin() {

        try {
            result = new Helper.signInHelper().execute(userName.getText().toString(), passWord.getText().toString()).get();
            if (result != null) {

                try {
                    if (result.isEmpty()) {
                        progressDialog.dismiss();
//                           AlertDialog alertDialog = new AlertDialog.Builder(SignInActivity.this).create();
//                           alertDialog.setMessage("User Id & Password are not Correct");
//                           alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                                   new DialogInterface.OnClickListener() {
//                                       public void onClick(DialogInterface dialog, int which) {
//                                           dialog.dismiss();
//                                       }
//                                   });
//                           alertDialog.show();

                    } else {

                        try {
                            Gson gson = new Gson();
                            UserLogin.RootObject jsonbody = gson.fromJson(result, UserLogin.RootObject.class);
                            if (jsonbody.equals(null)) {
                                Toast.makeText(SignInActivity.this, "User Id Password Incorrect", Toast.LENGTH_SHORT).show();
                            } else {


                                progressDialog.setMessage("Login in progress ...");
                                progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                                progressDialog.show();
                                if (jsonbody.isStatus()) {
                                    Token user = new Token();
                                    user.userRole = jsonbody.Role;
                                    user.access_token = jsonbody.login.access_token;
                                    user.userName = jsonbody.login.userName;
                                    user.token_type = jsonbody.login.token_type;
                                    user.expires_in = jsonbody.login.expires_in;
                                    user.userActive = jsonbody.Active;
                                    user.userID = jsonbody.UserID;

                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    if (!SharedPrefManager.getInstance(SignInActivity.this).getUser().access_token.equals(null)) {

                                        if (user.getUserRole().contains("Admin")) {
                                            progressDialog.dismiss();
                                            intent = new Intent(getApplicationContext(), HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                                        } else if (user.getUserActive(true)) {
                                            progressDialog.dismiss();
                                            intent = new Intent(SignInActivity.this, HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "User Is Not Active", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Invalid user id or password", Toast.LENGTH_SHORT).show();

                                }
                            }

                            }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {

                    Log.d("Exception", e.getMessage());
                }

            } else {
                Toast.makeText(getApplicationContext(), "Server not Connected", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Exception", e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(buttonClick);
        switch (v.getId()) {
            case R.id.signinButton:
                try {
                    if (userName.getText().toString().isEmpty() && passWord.getText().toString().isEmpty()) {
                        userName.setError("Required");
                        passWord.setError("Required");
                    } else if (!userName.getText().toString().isEmpty()) {

                        if (validation()) {
                            if (passWord.getText().toString().length() < 6) {
                                Toast.makeText(SignInActivity.this, "Password must be minimum 6 character ", Toast.LENGTH_SHORT).show();
                            } else {
                                userLogin();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Exception", e.getMessage());
                }
                break;
            case R.id.signupHere:
                intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void checkInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network

            connected = true;
            //    Toast.makeText(getApplicationContext(), "Internet is On", Toast.LENGTH_SHORT).show();
        } else {
            connected = false;
            Snackbar.make(getWindow().getDecorView().getRootView(), "No internet connection, retry", Snackbar.LENGTH_LONG).show();

        }
    }

    private boolean validation() {

        String Username = userName.getText().toString();
        String UserPass = passWord.getText().toString();
        boolean noSpecialChar = UserPass.matches("[a-zA-Z0-9 ]*");

        if (Username.trim().isEmpty()) {
            userName.setError("Required!");
        } else if (UserPass.trim().isEmpty()) {
            passWord.setError("Required!");
        } else if (noSpecialChar) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "Password Pattern is Aa@123", Snackbar.LENGTH_LONG).show();

        }
        return true;

    }
}
