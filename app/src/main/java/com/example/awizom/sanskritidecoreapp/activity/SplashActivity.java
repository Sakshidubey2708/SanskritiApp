package com.example.awizom.sanskritidecoreapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    String sendParam;
    private Intent intent;
    boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initview();
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    private void initview() {

        checkInternet();

        try {
            new Handler().postDelayed(new Runnable() {
                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */
                @Override
                public void run() {
                    try {
                        if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                            try {
                            if (SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserRole().equals("Admin")) {
                                {
                                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                                }
                            } else {
                                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                            }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } else {
                            Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }, SPLASH_TIME_OUT);

        }catch (Exception e){
            e.printStackTrace();
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

}
