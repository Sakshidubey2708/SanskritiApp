package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.model.DataOrder;

public class SearchListActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView searchByName, searchByNumber;
    private Intent intent;
    //    Fragment fragment = null;
    private DataOrder orderitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        initView();
    }

    private void initView() {

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search Activity");

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

        orderitem = new DataOrder();
        searchByName = findViewById(R.id.searchbyName);
        searchByNumber = findViewById(R.id.searchbyNumber);
        searchByName.setOnClickListener(this);
        searchByNumber.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchbyName:
                intent = new Intent(this, SearchDetailsListActivity.class);
                intent = intent.putExtra("StatusName", "Search By Name");
                intent = intent.putExtra("OrderID", String.valueOf(orderitem.OrderID));
                intent = intent.putExtra("InputTypeName", "ByName");

                startActivity(intent);
                break;
            case R.id.searchbyNumber:
                intent = new Intent(this, SearchDetailsListActivity.class);
                intent = intent.putExtra("StatusName", "Search By Number");
                intent = intent.putExtra("OrderID", String.valueOf(orderitem.OrderID));
                intent = intent.putExtra("InputTypeName", "ByNumber");
                startActivity(intent);
                break;

        }
    }
/////////////
}
