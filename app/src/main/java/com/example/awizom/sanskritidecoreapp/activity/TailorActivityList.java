package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.adapter.TailorAdapter;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.modelnew.TailorcatagoryModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class TailorActivityList extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String result = "";
    List<TailorcatagoryModel> tailorcatagoryModels;
    TailorAdapter adapter;
    ImageButton addtelor;
    private Button add, cancel;
    private EditText t_name, t_contact;
    private String telornamet, telorname_old;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tailor_list_activities_layout);
        initView();
    }

    private void initView() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tailor List");
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

        recyclerView = findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        addtelor = findViewById(R.id.addtelor);

        addtelor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.telor_dialog, null);
                alertbox.setView(dialogView);
                alertbox.setCancelable(false);
                t_name = dialogView.findViewById(R.id.sNo);
                t_contact = dialogView.findViewById(R.id.telornumber);
                add = dialogView.findViewById(R.id.add);
                cancel = dialogView.findViewById(R.id.cancelButton);
                alertbox.setTitle("Add telor");
                final AlertDialog b = alertbox.create();
                b.show();

                //   telorname=t_name.getText().toString();


                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (t_name.getText().toString().isEmpty() || t_contact.getText().toString().trim()
                                .length() < 10) {
                            t_name.setError("Required !");
                            t_contact.setError("Required !");
                        } else {

                            postTelorLists();
                            t_name.setText("");
                            t_contact.setText("");
                            b.dismiss();
                        }

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.dismiss();


                    }
                });
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getTailorList();
            }
        });

        getTailorList();
    }

    private void getTailorList() {

        try {
            mSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetTailorsListDetails().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<TailorcatagoryModel>>() {
                }.getType();
                tailorcatagoryModels = new Gson().fromJson(result, listType);
                Log.d("Error", tailorcatagoryModels.toString());
                adapter = new TailorAdapter(this, tailorcatagoryModels);
                recyclerView.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        } catch (Exception e) {
            mSwipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();

        }
    }

    private void postTelorLists() {

        telornamet = t_name.getText().toString();
        telorname_old = t_contact.getText().toString();
        try {

            new PostTailorAdd().execute(t_name.getText().toString().trim(), t_contact.getText().toString().trim());
            Toast.makeText(TailorActivityList.this, "Successfully Tailor Added ", Toast.LENGTH_SHORT).show();
            getTailorList();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static final class PostTailorAdd extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String t_name = params[0];
            String t_contact = params[1];

            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "TailorCatagoryPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("t_id", "");
                parameters.add("t_name", t_name);
                parameters.add("t_contact", t_contact);
                builder.post(parameters.build());
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

                } else {
                    super.onPostExecute(result);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

