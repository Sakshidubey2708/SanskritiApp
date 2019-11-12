package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.adapter.MesurementListAdapter;
import com.example.awizom.sanskritidecoreapp.adapter.RoomAdapter;
import com.example.awizom.sanskritidecoreapp.adapter.TailorAdapter;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.modelnew.MeasurementModel;
import com.example.awizom.sanskritidecoreapp.modelnew.RoomModel;
import com.example.awizom.sanskritidecoreapp.modelnew.TailorcatagoryModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.example.awizom.sanskritidecoreapp.R.layout.mesurement_list_activity;

public class MesurementListActivity extends AppCompatActivity {
    private Context mCtx;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String result = "";
    MesurementListAdapter adapter;
    ArrayList<String> measurment_type = new ArrayList<>();
    ImageButton addmesurement;
    private Button add, cancel;
    private EditText t_name;
    private ProgressDialog progressDialog;
    private MeasurementModel measurementModel;
    private List<MeasurementModel> measurementModelList;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mesurement_list_activity);
        initView();
    }

    private void initView() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Measurement List");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(MesurementListActivity.this, R.anim.slide_in, R.anim.slide_out);
                onBackPressed();
            }
        });

        toolbar.setSubtitleTextAppearance(MesurementListActivity.this, R.style.styleA);
        toolbar.setTitleTextAppearance(MesurementListActivity.this, R.style.styleA);
        toolbar.setTitleTextColor(Color.WHITE);
        progressDialog = new ProgressDialog(this);
        recyclerView = findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MesurementListActivity.this));

        addmesurement = findViewById(R.id.addmeasurement);

        addmesurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.measurement_dialog, null);
                alertbox.setView(dialogView);
                alertbox.setCancelable(false);
                t_name = dialogView.findViewById(R.id.sNo);
                add = dialogView.findViewById(R.id.add);
                cancel = dialogView.findViewById(R.id.cancelButton);
                alertbox.setTitle("Add Measurement");
                final AlertDialog b = alertbox.create();
                b.show();

                //   telorname=t_name.getText().toString();


                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (measurment_type.contains(t_name.getText().toString())) {
                            t_name.setError("All Ready Exist");
                            t_name.requestFocus();
                        } else if (t_name.getText().toString().isEmpty()) {
                            t_name.setError("Required !");
                        } else {
                            postMeasurment();
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
                GetMeasurementDetails();
            }
        });

        GetMeasurementDetails();
    }


    private void GetMeasurementDetails() {
        try {
            mSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetMasterMeasurementDetails().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<MeasurementModel>>() {
                }.getType();
                measurementModelList = new Gson().fromJson(result, listType);

                for (int i = 0; i < measurementModelList.size(); i++) {
                    measurment_type.add(measurementModelList.get(i).getUmo_type().toString());
                }
                adapter = new MesurementListAdapter(MesurementListActivity.this, measurementModelList);
                recyclerView.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void postMeasurment() {
        String mname = t_name.getText().toString();
        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            result = new OrderHelper.PostMasterMeasurment().execute(mname.trim(), SharedPrefManager.getInstance(MesurementListActivity.this).getUser().access_token).get();
            progressDialog.dismiss();
            try {
                if (result.isEmpty()) {
                    progressDialog.dismiss();
                    //Toast.makeText(this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    Toast.makeText(this, "Measurement Add Successful", Toast.LENGTH_SHORT).show();
                    GetMeasurementDetails();
                }

            } catch (Exception e) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
