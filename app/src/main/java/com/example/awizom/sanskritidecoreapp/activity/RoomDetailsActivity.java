package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.adapter.OrderItemAdapter;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.Catelog;
import com.example.awizom.sanskritidecoreapp.model.CatelogOrderDetailModel;
import com.example.awizom.sanskritidecoreapp.model.DataOrder;
import com.example.awizom.sanskritidecoreapp.model.ElightBottomModel;
import com.example.awizom.sanskritidecoreapp.model.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RoomDetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnLongClickListener {

    private TextView customerName, customerMobileNo, customerSno, customerOrder, customerDate, customerhall;
    private ImageButton additionButton;
    private TextView elight, roman, aPlat, elightPrice, romanPrice, aPlotPrice, totalAmount;
    private String catalogNameOne;
    private RelativeLayout relative_Layout_press, relativeLayout_edit_dailog, bottom_relative_press1;
    private RecyclerView recyclerView;
    private EditText editElight, editRoman, editAplot, ElightPrice, RomanPrice, APlotPrice;
    private Button updateBottom, cancelElight, allok;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressDialog progressDialog;
    CatelogOrderDetailModel catelogOrderDetailModel;
    List<CatelogOrderDetailModel> orderList;
    ElightBottomModel morder;
    ArrayAdapter<String> catadapter;
    ArrayAdapter<String> designadapter;
    OrderItemAdapter adapter;
    private Intent intent;
    private EditText s_no, pageNo, price, price2, qty, aQty;
    private AutoCompleteTextView catlogName, design;
    private Spinner unitSpinner, materialType;
    private Button addButton, cancelButton;
    String actualorder = "";
    String[] designlist;
    //  private AlertDialog b;


    private String roomName, orderID, customernAME, mobileNumber, orderDate, advance, StatusName, filterkey, buttonname, tailorList;
    DataOrder orderitem;
    private Double priceValue, price2value, result = Double.valueOf(0), superResult = Double.valueOf(0);
    private LinearLayout elightLayout;
    private ArrayList<String> catlogNewlist;

    // private Toolbar toolbar;private TextView textView; private ImageButton arrow_id_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_details);
        initView();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

    private void initView() {

        getSupportActionBar().setTitle("Room Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        roomName = getIntent().getExtras().getString("RoomName", "");
        orderID = String.valueOf(getIntent().getIntExtra("OrderID", 0));
        customernAME = getIntent().getExtras().getString("CustomerName", "");
        mobileNumber = getIntent().getExtras().getString("Mobile", "");
        orderDate = getIntent().getExtras().getString("OrderDate", "");
        advance = String.valueOf(getIntent().getDoubleExtra("Advance", 0));
        actualorder = getIntent().getExtras().getString("ActualOrder", "");

        filterkey = getIntent().getExtras().getString("FilterKey", "");
        StatusName = getIntent().getExtras().getString("StatusName", "");
        buttonname = getIntent().getExtras().getString("ButtonName", "");
        tailorList = getIntent().getExtras().getString("TailorList", "");


        customerName = findViewById(R.id.customer_name);
        customerMobileNo = findViewById(R.id.customer_mobile_no);
        customerOrder = findViewById(R.id.order_date);
        customerhall = findViewById(R.id.room_name);
        // allok=findViewById(R.id.allOkButtton);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        customerName.setText(customernAME);
        customerMobileNo.setText(mobileNumber);
        String date[] = orderDate.split("T", 0);
        customerOrder.setText(date[0]);
        customerhall.setText(roomName);

        elight = findViewById(R.id.elight_value);
        roman = findViewById(R.id.roman_value);
        aPlat = findViewById(R.id.aPlat_value);
        //elightPrice,romanPrice,aPlotPrice
        elightPrice = findViewById(R.id.elightprice_value);
        romanPrice = findViewById(R.id.romanprice_value);
        aPlotPrice = findViewById(R.id.aPlatprice_value);
        totalAmount = findViewById(R.id.total_value);
        additionButton = findViewById(R.id.updateButton);

        relative_Layout_press = findViewById(R.id.bottom_relative_press);
        relative_Layout_press.setOnClickListener(this);
        bottom_relative_press1 = findViewById(R.id.bottom_relative_press1);
        bottom_relative_press1.setOnClickListener(this);
        elight.setOnLongClickListener(this);
        roman.setOnLongClickListener(this);
        aPlat.setOnLongClickListener(this);

        catlogNewlist = new ArrayList<String>();

        progressDialog = new ProgressDialog(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        additionButton.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getFunctioncall();
            }
        });
        getFunctioncall();
        getElightBottom();

        catelogOrderDetailModel = new CatelogOrderDetailModel();
//        allok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                allOkButtonRoomLevel();
//            }
//        });

    }

    private void allOkButtonRoomLevel() {

        try {

            //     progressDialog.setMessage("loading...");
            //      progressDialog.show();
            new allokButtonRoomLevel().execute(SharedPrefManager.getInstance(getApplicationContext()).getUser().access_token);

        } catch (Exception e) {

            e.printStackTrace();
            //       progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();

        }
    }

    private class allokButtonRoomLevel extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            orderitem = new DataOrder();
            catelogOrderDetailModel = new CatelogOrderDetailModel();
            String accesstoken = params[0];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "OrderStatusPostNew");
                builder.addHeader("Content-Type", "application/json");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);

                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("OrderID", String.valueOf(orderitem.getOrderID()));
                parameters.add("RoomName", catelogOrderDetailModel.getRoomName());
                parameters.add("OrderItemID", "0");
                parameters.add("StatusName", "OrderPlaced");
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //      progressDialog.dismiss();
//                Toast.makeText(mCtx, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
            return json;
        }

        protected void onPostExecute(String result) {
            if (result.isEmpty()) {
                progressDialog.dismiss();
                //     Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                final Result jsonbodyres = gson.fromJson(result, Result.class);
                Toast.makeText(getApplicationContext(), jsonbodyres.getMessage(), Toast.LENGTH_SHORT).show();
                if (jsonbodyres.getStatus() == true) {


                }
                //       progressDialog.dismiss();
            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.updateButton:
                initViewByAlertdailog();
                break;
            case R.id.bottom_relative_press:
                //     dilogShow();
                break;
            case R.id.bottom_relative_press1:
                //  dilogShow();
                break;
        }
    }


    private void dilogShow() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_bottom_item, null);
        dialogBuilder.setView(dialogView);

        editElight = dialogView.findViewById(R.id.ediElight);
        editRoman = dialogView.findViewById(R.id.ediRoman);
        editAplot = dialogView.findViewById(R.id.ediAplot);
        ElightPrice = dialogView.findViewById(R.id.elighPrice);
        RomanPrice = dialogView.findViewById(R.id.romaPrice);
        APlotPrice = dialogView.findViewById(R.id.aPlotPrice);

        updateBottom = dialogView.findViewById(R.id.updateElight);
        cancelElight = dialogView.findViewById(R.id.cancelElight);
        editElight.setText(morder.Elight.toString());
        editRoman.setText(morder.Roman.toString());
        editAplot.setText(morder.APlat.toString());

        ElightPrice.setText(String.valueOf(morder.getElightPrice()));
        RomanPrice.setText(String.valueOf(morder.getRomanPrice()));
        APlotPrice.setText(String.valueOf(morder.getAPlatPrice()));
        dialogBuilder.setTitle("Edit bottom");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        updateBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((editElight.getText().toString().isEmpty()) || (editRoman.getText().toString().isEmpty())
                        || (editRoman.getText().toString().isEmpty()) || (editAplot.getText().toString().isEmpty())) {

                    editElight.setError("Field is required!");

                } else {
                    String elight = editElight.getText().toString();
                    String roman = editRoman.getText().toString();
                    String aplot = editAplot.getText().toString();
                    String elightprice = ElightPrice.getText().toString();
                    String romanprice = RomanPrice.getText().toString();
                    String aplotprice = APlotPrice.getText().toString();
                    try {

                        progressDialog.setMessage("loading...");
                        progressDialog.show();
                        new RoomDetailsActivity.POSTElight().execute(roomName.trim(), String.valueOf(orderID).trim(), elight, roman, aplot, SharedPrefManager.getInstance(getApplicationContext()).getUser().access_token, elightprice, romanprice, aplotprice);
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
                    }

                    b.dismiss();
                }
            }


        });

        cancelElight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.elight:
                dilogShow();
                break;
            case R.id.aPlat:
                dilogShow();
                break;
            case R.id.roman:
                dilogShow();
                break;
        }
        return false;
    }

    private void initViewByAlertdailog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_dailog_layout, null);
        dialogBuilder.setView(dialogView);

        s_no = dialogView.findViewById(R.id.sNo);
        catlogName = dialogView.findViewById(R.id.catlogName);
        design = dialogView.findViewById(R.id.design);
        pageNo = dialogView.findViewById(R.id.pageNo);
        price = dialogView.findViewById(R.id.price);
        price2 = dialogView.findViewById(R.id.price2);
        elightLayout = dialogView.findViewById(R.id.elightLinearLayout);


        elight = dialogView.findViewById(R.id.ediElight);
        roman = dialogView.findViewById(R.id.ediRoman);
        aPlat = dialogView.findViewById(R.id.ediAplot);
        elightPrice = dialogView.findViewById(R.id.elighPrice);
        romanPrice = dialogView.findViewById(R.id.romaPrice);
        aPlotPrice = dialogView.findViewById(R.id.aPlotPrice);


        materialType = dialogView.findViewById(R.id.materialType);
        qty = dialogView.findViewById(R.id.qTy);
        aQty = dialogView.findViewById(R.id.aQty);
        unitSpinner = dialogView.findViewById(R.id.unit);
        getCatalogList();
        addButton = dialogView.findViewById(R.id.add);
        cancelButton = dialogView.findViewById(R.id.cancelButton);


        // dialogBuilder.setTitle("Add Order");
        final AlertDialog b = dialogBuilder.create();
        b.show();


        catlogName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                design.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                ///getDesignList();


                try {
                    if (catlogName.getText().length() > 0) {
                        getDesignList();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String snumber, catlogname, desiGn, page_no, priCe, priCe2, qTy, aqty, materialtype, unIt, eligt, romn, aplot, elightprice, romanprice, aplotprice;

                try {
                    if ((catlogName.getText().toString().isEmpty())) {
                        catlogName.setError("Catalog name is required!");

                    } else if (design.getText().toString().isEmpty()) {
                        design.setError("Design is required!");
                    } else {

                        if (aQty.getText().toString().isEmpty()) {
                            aqty = "1";
                        } else {
                            aqty = aQty.getText().toString();
                        }
                        if (pageNo.getText().toString().isEmpty()) {
                            page_no = "nill";
                        } else {
                            page_no = pageNo.getText().toString();
                        }
                        if (qty.getText().toString().isEmpty()) {
                            qTy = "1";
                        } else {
                            qTy = qty.getText().toString();
                        }

                        if (elight.getText().toString().isEmpty()) {
                            eligt = "0";
                        } else {
                            eligt = elight.getText().toString();
                        }
                        if (roman.getText().toString().isEmpty()) {
                            romn = "0";
                        } else {
                            romn = roman.getText().toString();
                        }
                        if ((aPlat.getText().toString().isEmpty())) {
                            aplot = "0";
                        } else {
                            aplot = aPlat.getText().toString();
                        }
                        if ((elightPrice.getText().toString().isEmpty())) {

                            elightprice = "0";
                        } else {
                            elightprice = elightPrice.getText().toString();

                        }
                        if ((romanPrice.getText().toString().isEmpty())) {
                            romanprice = "0";
                        } else {
                            romanprice = romanPrice.getText().toString();
                        }
                        if ((aPlotPrice.getText().toString().isEmpty())) {
                            aplotprice = "0";
                        } else {
                            aplotprice = aPlotPrice.getText().toString();
                        }
                        if ((price2.getText().toString().isEmpty())) {
                            priCe2 = "0";
                        } else {
                            priCe2 = price2.getText().toString();
                        }
                        if ((price.getText().toString().isEmpty())) {
                            priCe = "0";
                        } else {
                            priCe = price.getText().toString();
                        }

                        snumber = s_no.getText().toString();
                        catlogname = catlogName.getText().toString();
                        desiGn = design.getText().toString();


                        materialtype = materialType.getSelectedItem().toString();
                        unIt = unitSpinner.getSelectedItem().toString();


                        priceValue = Double.parseDouble(priCe);
                        price2value = Double.parseDouble(priCe2);
                        result = ((priceValue * price2value) / 100);
                        superResult = priceValue - result;
                        price2.setText(String.valueOf(superResult));

                        progressDialog.setMessage("loading...");
                        progressDialog.show();
                        if (actualorder.equals("ActualOrder")) {
                            new POSTOrder().execute("0", materialtype, String.valueOf(superResult), "0", qTy, unIt, "0", catlogname, snumber, desiGn, page_no, priCe,
                                    unIt, "0", roomName, orderID, eligt, romn, aplot, elightprice, romanprice, aplotprice, SharedPrefManager.getInstance(RoomDetailsActivity.this).getUser().access_token);

                        } else {
                            new POSTOrder().execute("0", materialtype, String.valueOf(superResult), qTy, qTy, unIt, "0", catlogname, snumber, desiGn, page_no, priCe,
                                    unIt, "0", roomName, orderID, eligt, romn, aplot, elightprice, romanprice, aplotprice, SharedPrefManager.getInstance(RoomDetailsActivity.this).getUser().access_token);

                        }

                        b.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();

                }

            }


        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
                /*
                 * we will code this method to delete the artist
                 * */
            }
        });

        materialType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  String item = parent.getItemAtPosition(position).toString();
                String selectedDiv = adapterView.getItemAtPosition(i).toString();

                if (selectedDiv.equals("Curtain")) {
                    elightLayout.setVisibility(View.VISIBLE);
                } else {
                    elightLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (materialType.getSelectedItem().toString().equals("Curtain")) {
            elightLayout.setVisibility(View.VISIBLE);
        } else if (!materialType.getSelectedItem().toString().equals("Curtain")) {
            elightLayout.setVisibility(View.GONE);
        }
    }

    private boolean validation() {

        boolean status = true;
        if ((catlogName.getText().toString().isEmpty())) {
            catlogName.setError("Catalog name is required!");
            status = false;
        } else if (design.getText().toString().isEmpty()) {
            design.setError("Design is required!");
            status = false;
        } else if (aQty.getText().toString().isEmpty()) {
            aQty.setText("1");
            status = true;
        } else if (pageNo.getText().toString().isEmpty()) {
            pageNo.setText("1");
            status = true;
        } else if (qty.getText().toString().isEmpty()) {
            qty.setText("1");
            status = true;
        } else if (price.getText().toString().isEmpty()) {
            price.setText("0");
            status = true;
        } else if (price2.getText().toString().isEmpty()) {
            price2.setText("0");
            status = true;
        } else if (elight.getText().toString().isEmpty()) {
            elight.setText("0");
            status = true;
        } else if (roman.getText().toString().isEmpty()) {
            roman.setText("0");
            status = true;
        } else if ((aPlat.getText().toString().isEmpty())) {
            aPlat.setText("0");
            status = true;
        } else if ((elightPrice.getText().toString().isEmpty())) {
            elightPrice.setText("0");
            status = true;
        } else if ((romanPrice.getText().toString().isEmpty())) {
            romanPrice.setText("0");
            status = true;
        } else if ((aPlotPrice.getText().toString().isEmpty())) {
            aPlotPrice.setText("0");
            status = true;
        }


        return status;

    }

    private void getFunctioncall() {

        try {
            mSwipeRefreshLayout.setRefreshing(true);
            // progressDialog.setMessage("loading...");
            //  progressDialog.show();
            new RoomDetailsActivity.detailsGET().execute(roomName.toString().trim(), orderID, SharedPrefManager.getInstance(this).getUser().access_token);
        } catch (Exception e) {
            e.printStackTrace();
            mSwipeRefreshLayout.setRefreshing(false);
            //progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private class detailsGET extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String roomName = strings[0];
            String orderID = strings[1];
            String accesstoken = strings[2];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "OrderItemGet/" + orderID.trim() + "/" + roomName.trim());
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // progressDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            }

            return json;

        }

        protected void onPostExecute(String result) {
            try {

                if (result.isEmpty()) {
                    //progressDialog.dismiss();
                    mSwipeRefreshLayout.setRefreshing(false);
                    //            Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<CatelogOrderDetailModel>>() {
                    }.getType();
                    orderList = new Gson().fromJson(result, listType);
                    adapter = new OrderItemAdapter(RoomDetailsActivity.this, orderList, actualorder, filterkey, StatusName, buttonname, tailorList, roomName);
                    recyclerView.setAdapter(adapter);
                    // progressDialog.dismiss();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class POSTOrder extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            //     InputStream inputStream

            String orderItemId = params[0];
            String materialtype = params[1];
            String priCe2 = params[2];
            String qTy = params[3];
            String aqty = params[4];
            String orderUnit = params[5];
            String orderRoomId = params[6];

            String catlogname = params[7];
            String snumber = params[8];
            String desiGn = params[9];
            String page_no = params[10];
            String priCe = params[11];
            String unit = params[12];
            String catalogID = params[13];
            String roomName = params[14];
            String orderID = params[15];

            String elight = params[16];
            String roman = params[17];
            String aPlat = params[18];
            String elightprice = params[19];
            String romanprice = params[20];
            String aPlatprice = params[21];
            String accesstoken = params[22];


            String json = "";
            try {

                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "OrderItemPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);

                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("OrderItemID", orderItemId);
                parameters.add("MaterialType", materialtype);
                parameters.add("Price2", priCe2);
                parameters.add("Qty", qTy);
                parameters.add("AQty", aqty);
                parameters.add("OrderUnit", orderUnit);
                parameters.add("OrderRoomID", orderRoomId);

                parameters.add("CatalogName", catlogname);
                parameters.add("SerialNo", snumber);
                parameters.add("Design", desiGn);
                parameters.add("PageNo", page_no);
                parameters.add("Price", priCe);

                parameters.add("Unit", unit);
                parameters.add("CatalogID", catalogID);

                parameters.add("RoomName", roomName.trim());
                parameters.add("OrderID", orderID.trim());

                parameters.add("Elight", elight);
                parameters.add("Roman", roman);
                parameters.add("APlat", aPlat);
                parameters.add("ElightPrice", elightprice);
                parameters.add("RomanPrice", romanprice);
                parameters.add("APlatPrice", aPlatprice);

                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            }
            return json;
        }

        protected void onPostExecute(String result) {

            if (result.isEmpty()) {
                progressDialog.dismiss();
                //         Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                final Result jsonbodyres = gson.fromJson(result, Result.class);
                Toast.makeText(getApplicationContext(), jsonbodyres.getMessage(), Toast.LENGTH_SHORT).show();
                if (jsonbodyres.getStatus() == true) {
                    getFunctioncall();
                    getElightBottom();
                }
                progressDialog.dismiss();
            }
        }
    }

    private void getElightBottom() {
        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            new elightdetailsGET().execute(roomName.toString().trim(), orderID, SharedPrefManager.getInstance(this).getUser().access_token);

        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private class elightdetailsGET extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String roomName = strings[0];
            String orderID = strings[1];
            String accesstoken = strings[2];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "RoomGet/" + orderID.trim() + "/" + roomName.trim());
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
                Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            }
            return json;
        }

        protected void onPostExecute(String result) {

            if (result.isEmpty()) {
                progressDialog.dismiss();
                //      Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                Type getType = new TypeToken<ElightBottomModel>() {
                }.getType();
                morder = new Gson().fromJson(result, getType);

                elight.setText("Q. " + morder.getElight());

                roman.setText("SF. " + morder.getRoman());

                aPlat.setText("Q. " + morder.getAPlat());

                elightPrice.setText("P. " + morder.getElightPrice().toString());

                romanPrice.setText("P. " + morder.getRomanPrice().toString());

                aPlotPrice.setText("P. " + morder.getAPlatPrice().toString());

                if (actualorder.equals("ActualOrder")) {
                    totalAmount.setText(Double.toString(morder.getATotalAmount()));
                } else {
                    totalAmount.setText(Double.toString(morder.getTotalAmount()));
                }

                progressDialog.dismiss();

            }


        }
    }

    private class POSTElight extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            //  InputStream inputStream

            String roomname = params[0];
            String orderid = params[1];
            String elight = params[2];
            String roman = params[3];
            String aPlat = params[4];
            String accesstoken = params[5];

            String elightprice = params[6];
            String romanprice = params[7];
            String aPlatprice = params[8];

            String json = "";
            try {

                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "OrderRoomUpdatePost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);

                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("RoomName", roomname.trim());
                parameters.add("OrderID", orderid);
                parameters.add("Elight", elight);
                parameters.add("Roman", roman);
                parameters.add("APlat", aPlat);
                parameters.add("ElightPrice", elightprice);
                parameters.add("RomanPrice", romanprice);
                parameters.add("APlatPrice", aPlatprice);


                builder.post(parameters.build());


                okhttp3.Response response = client.newCall(builder.build()).execute();

                if (response.isSuccessful()) {
                    json = response.body().string();


                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            }
            return json;
        }

        protected void onPostExecute(String result) {

            if (result.isEmpty()) {
                progressDialog.dismiss();
                //       Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                final Result jsonbodyres = gson.fromJson(result, Result.class);
                Toast.makeText(getApplicationContext(), jsonbodyres.getMessage(), Toast.LENGTH_SHORT).show();
                if (jsonbodyres.getStatus() == true) {
                    getElightBottom();

                }
                progressDialog.dismiss();

            }


        }

    }

    private void getCatalogDesignSingle(String cname, String dname) {
        try {

            new getCatalogDesign().execute(cname, dname, SharedPrefManager.getInstance(this).getUser().access_token);
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private class getCatalogDesign extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[2];
            String catalogName = strings[0];
            String designName = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CatalogGet/" + catalogName + "/" + designName);
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
                Toast.makeText(RoomDetailsActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
            return json;
        }

        protected void onPostExecute(String result) {
            if (result.isEmpty()) {

                //       Toast.makeText(RoomDetailsActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<Catelog>() {
                }.getType();
                Catelog catelogdesign = new Gson().fromJson(result, listType);
                if (catelogdesign != null) {
                    price.setText(String.valueOf(catelogdesign.getPrice()));
//                    qty.setText("");
//                    price2.setText("");
                    if (catelogdesign.getUnit().trim().length() > 0 || catelogdesign.getMaterialType().trim().length() > 0) {
                        unitSpinner.setSelection(((ArrayAdapter<String>) unitSpinner.getAdapter()).getPosition(catelogdesign.getUnit().toString()));
                        materialType.setSelection(((ArrayAdapter<String>) materialType.getAdapter()).getPosition(catelogdesign.getMaterialType().toString()));
                        if (!materialType.getSelectedItem().toString().equals("Curtain")) {
                            elightLayout.setVisibility(View.GONE);
                        } else if (materialType.getSelectedItem().toString().equals("Curtain")) {
                            elightLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
                //Getting the instance of AutoCompleteTextView
            }
        }
    }

    private void getCatalogList() {
        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            new getCatalog().execute(SharedPrefManager.getInstance(this).getUser().access_token);
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private class getCatalog extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CatalogGet");
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
                Toast.makeText(RoomDetailsActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
            return json;
        }

        protected void onPostExecute(String result) {
            if (result.isEmpty()) {
                progressDialog.dismiss();
                //        Toast.makeText(RoomDetailsActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<String[]>() {
                }.getType();
                String[] cateloglist = new Gson().fromJson(result, listType);


                catadapter = new ArrayAdapter<String>(RoomDetailsActivity.this, android.R.layout.select_dialog_item, cateloglist);
                catlogName.setThreshold(1);//will start working from first character
                catlogName.setAdapter(catadapter);//setting the adapter materialIdSelected into the AutoCompleteTextView


                //Getting the instance of AutoCompleteTextView
                progressDialog.dismiss();


            }


        }
    }

    private void getDesignList() {
        try {
            // progressDialog.setMessage("loading...");
            // progressDialog.show();
            new getDesign().execute(catlogName.getText().toString(), SharedPrefManager.getInstance(this).getUser().access_token);
        } catch (Exception e) {
            e.printStackTrace();
            // progressDialog.dismiss();
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private class getDesign extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String json = "";
            String catalogNameOne = strings[0];
            String accesstoken = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CatalogGet/" + catalogNameOne);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(RoomDetailsActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
            return json;
        }

        protected void onPostExecute(String result) {
            if (result.isEmpty()) {

                //        Toast.makeText(RoomDetailsActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
            } else {
//                Gson gson = new Gson();
//                Type listType = new TypeToken<String[]>() {
//                }.getType();
//                designlist = new Gson().fromJson(result, listType);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Catelog>>() {
                }.getType();
                List<Catelog> catelogdesign = new Gson().fromJson(result, listType);
                catlogNewlist.clear();
                for (Catelog forId : catelogdesign) {
                    catlogNewlist.add(forId.getDesign().toString());

                }
                if (catlogNewlist.size() > 0) {
                    getCatalogDesignSingle(catlogName.getText().toString(), catlogNewlist.get(0).toString());
                    // Toast.makeText(getApplicationContext(), catlogNewlist.get(0).toString() , Toast.LENGTH_SHORT).show();
                }
                designadapter = new ArrayAdapter<String>(RoomDetailsActivity.this, android.R.layout.select_dialog_item, catlogNewlist);
                design.setThreshold(1);//will start working from first character
                design.setAdapter(designadapter);//setting the adapter materialIdSelected into the AutoCompleteTextView


            }


        }
    }

}
