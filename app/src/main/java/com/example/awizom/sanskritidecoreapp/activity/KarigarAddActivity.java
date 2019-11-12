package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.graphics.Color;
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
import com.example.awizom.sanskritidecoreapp.adapter.KarigarAdapter;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.modelnew.KarigarModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class KarigarAddActivity extends  AppCompatActivity {

    RecyclerView recyclerView;
    private String result = "";
    List<KarigarModel> KarigarModels;
    KarigarAdapter adapter;
    ImageButton addRoom;
    private Button add, cancel;
    ArrayList<String> room_type = new ArrayList<>();
    private EditText room_name,contactKarigar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String telornamet, telorname_old;
    private long karigarIDs=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roomlist);
        initView();
    }

    private void initView() {

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Kaareegar List");
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

        addRoom = findViewById(R.id.updateButton);
        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.karigar_add_box, null);
                alertbox.setView(dialogView);
                alertbox.setCancelable(false);
                room_name = dialogView.findViewById(R.id.addText);
                contactKarigar = dialogView.findViewById(R.id.contactKarigar);
                add = dialogView.findViewById(R.id.addBtn);
                cancel = dialogView.findViewById(R.id.backBtn);
                alertbox.setTitle("Add Room");
                final AlertDialog b = alertbox.create();
                b.show();

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (room_type.contains(room_name.getText().toString())) {
                            room_name.setError("All Ready Exist");
                            room_name.requestFocus();
                        } else if (room_name.getText().toString().isEmpty()) {
                            room_name.setError("Required !");
                        } else {
                            postAddRoom();
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

        getRoomList();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRoomList();
            }
        });
    }

    private void getRoomList() {
        try {
            mSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetkarigarListDetails().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<KarigarModel>>() {
                }.getType();
                KarigarModels = new Gson().fromJson(result, listType);

                for (int i = 0; i < KarigarModels.size(); i++) {
                    room_type.add(KarigarModels.get(i).getKarigarName().toString());
                }
                Log.d("Error", KarigarModels.toString());
                adapter = new KarigarAdapter(this, KarigarModels);
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void postAddRoom() {
        String addtexttype = room_name.getText().toString();
        String contact = contactKarigar.getText().toString();
        try {
            result = new OrderHelper.PostKarigarDetail().execute(String.valueOf(karigarIDs),addtexttype.toString().trim(),contact.toString().trim()).get();
            try {
                if (result.isEmpty()) {
                } else {
                    Gson gson = new Gson();
                    Toast.makeText(this, "Room Add Successful", Toast.LENGTH_SHORT).show();
                    getRoomList();
                }

            } catch (Exception e) {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

