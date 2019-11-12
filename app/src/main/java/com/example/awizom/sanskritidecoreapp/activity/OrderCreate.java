package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.adapter.AlertMaterialAdapter;
import com.example.awizom.sanskritidecoreapp.adapter.CatalogMaterilAdapter;
import com.example.awizom.sanskritidecoreapp.adapter.CustomerOrderItemsAdapter;
import com.example.awizom.sanskritidecoreapp.adapter.OrderRoomAdapter;
import com.example.awizom.sanskritidecoreapp.adapter.RoomNewOrderAdapter;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.CustomerModel;
import com.example.awizom.sanskritidecoreapp.model.Result;
import com.example.awizom.sanskritidecoreapp.modelnew.CatalogCatagoryModel;
import com.example.awizom.sanskritidecoreapp.modelnew.CustomerOrderModel;
import com.example.awizom.sanskritidecoreapp.modelnew.MeasurementModel;
import com.example.awizom.sanskritidecoreapp.modelnew.RoomModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class OrderCreate extends AppCompatActivity implements View.OnClickListener {

    int lebgth;
    EditText setdate;
    private AutoCompleteTextView customerName, custContact;
    String result = "", customerContact = "", materialIdSelected = "", roomIdSelected = "";
    ArrayList<String> idselect = new ArrayList<String>();
    private long customerId = 0;
    Calendar myCalendar = Calendar.getInstance();
    ArrayAdapter<String> adapter;
    private CustomerModel dataOrderValue;
    private List<CustomerModel> customerlist;
    private String[] customerNameList;
    private String[] orderMRpAmount;
    private String[] customerMobileList;
    private String[] roomNameList;
    private RoomModel roomModel;
    private List<RoomModel> roomModelList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView pagerecyclerView;
    private RecyclerView roomRecyls;
    List<RoomModel> RoomModels;
    RoomNewOrderAdapter roomAdapter;
    ArrayList<String> room_type = new ArrayList<>();
    private Button orderBtn, nextorderBtn, proceedButton;
    private MeasurementModel measurementModel;
    private List<MeasurementModel> measurementModelList;
    List<String> uIds = new ArrayList<String>();
    List<String> uNames = new ArrayList<String>();
    String o_id = "",oritemID="";
    private long umo_id = 0, order_id = 0, OrderItemID = 0,tailorCalculationId=0, catalogId =0,catalogItemID=0;
    private CustomerOrderItemsAdapter customerOrderItemsAdapter;
    private List<CustomerOrderModel> customerOrderModelList;
    private List<CustomerOrderModel> customerOrderModelLists;
    private RoomNewOrderAdapter roomadapters;
    private AlertMaterialAdapter materialAdapter;
    private SwipeRefreshLayout roommSwipeRefreshLayout;
    private RecyclerView RoomsrecyclerView;
    private RecyclerView MaterialrecyclerView;
    private String custmerNames = "", mId = "", contact = "", materialType = "", Roll = "";
    private SwipeRefreshLayout materialmSwipeRefreshLayout;
    private static int SPLASH_TIME_OUT = 10;
    private SwipeRefreshLayout newCatalogForMultipleSwipeRefreshLayout;
    private RecyclerView newCatalogForMultipleMaterialrecyclerView;
    private CatalogMaterilAdapter catalogMaterilAdapter;
    List<CatalogCatagoryModel> catalogCatagoryModels;
    public RoomModel pricingView;
    ArrayList<String> designName = new ArrayList<String>();
    ArrayList<String> catalogIDs = new ArrayList<String>();
    ArrayList<String> pageno = new ArrayList<String>();
    ArrayList<String> quantity = new ArrayList<String>();
    ArrayList<String> mrp = new ArrayList<String>();
    ArrayList<String> quality = new ArrayList<String>();
    ArrayList<String> discount = new ArrayList<String>();
    ArrayList<String> roomSelected = new ArrayList<String>();
    ArrayList<String> materIdSelected = new ArrayList<String>();
    ArrayList<String> afterDicountamt = new ArrayList<String>();
    ArrayList<String> CatalogItemSID = new ArrayList<String>();
    TextView roomeNames;
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_create_activity);
        try {
            initializePart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializePart() {

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order Create");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(OrderCreate.this, R.anim.slide_in, R.anim.slide_out);
                onBackPressed();
            }
        });
        toolbar.setSubtitleTextAppearance(OrderCreate.this, R.style.styleA);
        toolbar.setTitleTextAppearance(OrderCreate.this, R.style.styleA);
        toolbar.setTitleTextColor(Color.WHITE);

        custmerNames = getIntent().getStringExtra("CustomerName");
        contact = getIntent().getStringExtra("Mobile");

        roomRecyls = findViewById(R.id.roomRecyls);
        roomRecyls.setHasFixedSize(true);
        roomRecyls.setLayoutManager(new LinearLayoutManager(this));

        roomeNames = findViewById(R.id.roomeName);
        pagerecyclerView = findViewById(R.id.mPagerecyclerView);
        pagerecyclerView.setHasFixedSize(true);
        pagerecyclerView.setLayoutManager(new LinearLayoutManager(this));




        customerName = findViewById(R.id.customer_name);
        custContact = findViewById(R.id.customer_contact);
        setdate = findViewById(R.id.date);
        orderBtn = findViewById(R.id.OrderAdd);
        nextorderBtn = findViewById(R.id.NextOrder);
        orderBtn.setOnClickListener(this);
        nextorderBtn.setOnClickListener(this);
        proceedButton = findViewById(R.id.ProceedBtn);
        proceedButton.setOnClickListener(this);
        mSwipeRefreshLayout = findViewById(R.id.mpageswipeRefreshLayout);
        hideSoftKeyboard();

        GetCustomerName();
        GetCustomerMobile();
        try {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Refresh items
                    GetCustomerName();
                    GetCustomerMobile();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            customerName.setText(custmerNames);
            custContact.setText(contact);
//            GetCustomerName(customerName.getText().toString());
            GetCustomerNumber(custContact.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                custContact.setText("");
                if (customerName.getText().length() == 0) {
                    customerId = 0;
                } else {
                    GetCustomerName(customerName.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        customerName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showSoftKeyboard(v);
                proceedButton.setVisibility(View.GONE);
                return false;
            }
        });

        custContact.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showSoftKeyboard(v);
                proceedButton.setVisibility(View.GONE);
                return false;
            }
        });
        custContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                customerName.setText("");
                if (custContact.getText().length() == 0) {
                    customerId = 0;
                } else {
                    GetCustomerNumber(custContact.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }
        };
        String date_n = new SimpleDateFormat("yyyy/ MM/ dd", Locale.getDefault()).format(new Date());
        setdate.setText(date_n);
        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(OrderCreate.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);

    }

    private void GetCustomerOrderModel(final String oids) {

        try {

            mSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetOrderFinalItems().execute(oids.toString()).get();
            if (result.isEmpty()) {
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CustomerOrderModel>>() {
                }.getType();

//                customerOrderModelList = new Gson().fromJson(result, listType);
//                orderMRpAmount = new String[customerOrderModelList.size()];
//                for (int i = 0; i < customerOrderModelList.size(); i++) {
//                    orderMRpAmount[i] = String.valueOf(customerOrderModelList.get(i).getMrp());
//                }

                customerOrderModelList = new Gson().fromJson(result, listType);
                CustomerOrderItemsAdapter customerOrderItemsAdapter = new CustomerOrderItemsAdapter(this, customerOrderModelList);
                pagerecyclerView.setAdapter(customerOrderItemsAdapter);
               // GetCustomerFinalOrder(oids);
                for(int i=0; i< customerOrderModelList.size(); i++){
                    roomeNames.setText(customerOrderModelList.get(i).getRoom_type().toString());
                }
                proceedButton.setVisibility(View.GONE);
                Log.d("Error", customerOrderModelList.toString());


                proceedButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            ArrayList<String> mrps = new ArrayList<String>();
                            ArrayList<String> catalogDiscount = new ArrayList<String>();
                            ArrayList<String> AfterDiscountAmt = new ArrayList<String>();
                            ArrayList<String> orderItem_id = new ArrayList<String>();
                            ArrayList<String> ORderId = new ArrayList<String>();


                            for (int i = 0; i < customerOrderModelList.size(); i++) {
                                View view = pagerecyclerView.getChildAt(i);


//                            TextView textView1= (TextView) view.findViewById(R.id.catalogMrp);
//                            String mrpTotal=textView1.getText().toString().split("/-")[0];
//                            mrps.add(mrpTotal);

//                            TextView textView2=(TextView) view.findViewById(R.id.catalog_Discount);
//                            String cataDiscount=textView2.getText().toString().split("%")[0];
//                            catalogDiscount.add(cataDiscount);
//
//                            TextView textView3=(TextView) view.findViewById(R.id.RESULTCALCULATEaFTERdISCOUNT);
//                            String Afterdiscount=textView3.getText().toString().split("/-")[0];
//                            AfterDiscountAmt.add(Afterdiscount);

//                            TextView textView4=(TextView) view.findViewById(R.id.order_item_id);
//                            String orderItemIds=textView4.getText().toString();
//                            orderItem_id.add(orderItemIds);

//                            TextView textView5=(TextView) view.findViewById(R.id.orderId);
//                            String orderIds=textView5.getText().toString();
//                            ORderId.add(orderIds);


                            }


                            try {
                                Intent intent = new Intent(OrderCreate.this, ProceedAmount.class);
                                intent = intent.putExtra("ORDERID", oids);
                                //   intent = intent.putExtra("ORDERITEMID", orderItem_id);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            mSwipeRefreshLayout.setRefreshing(false);

        }
    }

    private void GetCustomerFinalOrder(final String oids){
        try {


            result = new OrderHelper.GetOrderFinal().execute(oids.toString()).get();
            if (result.isEmpty()) {

            } else {

                Gson gson = new Gson();
                Type listType = new TypeToken<List<CustomerOrderModel>>() {
                }.getType();

                customerOrderModelLists = new Gson().fromJson(result, listType);
                OrderRoomAdapter roomAdapters = new OrderRoomAdapter(this, customerOrderModelLists);
                roomRecyls.setAdapter(roomAdapters);
                GetCustomerOrderModel(oritemID);
                proceedButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intent = new Intent(OrderCreate.this, ProceedAmount.class);
                            intent = intent.putExtra("ORDERID", oids);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        setdate.setText(sdf.format(myCalendar.getTime()));
    }

    private void GetCustomerName(String customerNames) {
        try {
            result = new OrderHelper.GetCustomerNames().execute(SharedPrefManager.getInstance(OrderCreate.this).getUser().access_token, customerNames).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<CustomerModel>() {
                }.getType();
                dataOrderValue = new Gson().fromJson(result, listType);
                if (dataOrderValue != null) {
                    customerId = dataOrderValue.getCustomerID();
                    custContact.setText(String.valueOf(dataOrderValue.getMobile()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetCustomerName() {
        try {
            mSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetAllCustomerDetails().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CustomerModel>>() {
                }.getType();
                customerlist = new Gson().fromJson(result, listType);
                customerNameList = new String[customerlist.size()];
                for (int i = 0; i < customerlist.size(); i++) {
                    customerNameList[i] = String.valueOf(customerlist.get(i).getCustomerName());
                }
                adapter = new ArrayAdapter<String>(OrderCreate.this, android.R.layout.select_dialog_item, customerNameList);
                customerName.setThreshold(1);//will start working from first character
                customerName.setAdapter(adapter);//setting the adapter materialIdSelected into the AutoCompleteTextView
            }

        } catch (Exception e) {
            e.printStackTrace();
            //  Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void GetCustomerNumber(String s) {

        try {

            result = new OrderHelper.GetAllCustomerMobileDetails().execute(SharedPrefManager.getInstance(OrderCreate.this).getUser().access_token, s).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<CustomerModel>() {
                }.getType();
                dataOrderValue = new Gson().fromJson(result, listType);
                if (dataOrderValue != null) {
                    customerId = dataOrderValue.getCustomerID();
                    //customerName.setText(dataOrderValue.getCustomerName());

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetCustomerMobile() {
        try {
            mSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetAllCustomerMobileNo().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CustomerModel>>() {
                }.getType();
                customerlist = new Gson().fromJson(result, listType);
                customerMobileList = new String[customerlist.size()];
                for (int i = 0; i < customerlist.size(); i++) {
                    customerMobileList[i] = String.valueOf(customerlist.get(i).getMobile());
                }

                adapter = new ArrayAdapter<String>(OrderCreate.this, android.R.layout.select_dialog_item, customerMobileList);
                custContact.setThreshold(1);//will start working from first character
                custContact.setAdapter(adapter);//setting the adapter materialIdSelected into the AutoCompleteTextView;


            }

        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.OrderAdd:
                OrderPost();

                break;
            case R.id.NextOrder:
                new Handler().postDelayed(new Runnable() {
                    /*
                     * Showing splash screen with a timer. This will be useful when you
                     * want to show case your app logo / company
                     */
                    @Override
                    public void run() {
                        try {

                            openAlertforRoom();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, SPLASH_TIME_OUT);
                break;

        }
    }

    private void openAlertforRoom() {

        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.list_room_order, null);
        alertbox.setView(dialogView);
        alertbox.setCancelable(false);
        final Button proceed = dialogView.findViewById(R.id.ProceedBtn);
        final Button cancel = dialogView.findViewById(R.id.cancelBtn);
        final ImageButton addRoomButton = dialogView.findViewById(R.id.addButton);
        //  final SwipeRefreshLayout roommSwipeRefreshLayout;
//        final RecyclerView recyclerView;
        //       RoomNewOrderAdapter roomAdapter = null;
        roommSwipeRefreshLayout = dialogView.findViewById(R.id.swipeRefreshLayout);
        RoomsrecyclerView = dialogView.findViewById(R.id.recyclerView);
        RoomsrecyclerView.setHasFixedSize(true);
        RoomsrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        final AlertDialog b = alertbox.create();
        b.show();


        roommSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getRoomList();
            }
        });
        try {
            roommSwipeRefreshLayout.setRefreshing(false);
            result = new OrderHelper.GetRoomsDetail().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {
                roommSwipeRefreshLayout.setRefreshing(false);
            } else {
                roommSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<RoomModel>>() {
                }.getType();
                RoomModels = new Gson().fromJson(result, listType);
                for (int i = 0; i < RoomModels.size(); i++) {
                    room_type.add(RoomModels.get(i).getRoom_type().toString());
                }
                Log.d("Error", RoomModels.toString());
                roomAdapter = new RoomNewOrderAdapter(this, RoomModels);
                RoomsrecyclerView.setAdapter(roomAdapter);
                catalogItemID= Long.parseLong(null);
                catalogId= Long.parseLong(null);
                materialIdSelected="";

            }
        } catch (Exception e) {
            roommSwipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();

        }
        final RoomNewOrderAdapter finalRoomAdapter = roomAdapter;
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                roomIdSelected = "";
                List<RoomModel> stList = (finalRoomAdapter).getRoomlist();
                for (int p = 0; p < stList.size(); p++) {
                    RoomModel pricingView = stList.get(p);
                    if (pricingView.isSelected() == true) {
                        roomIdSelected = roomIdSelected + pricingView.getRoom_catagory_id() + ",";

                        new Handler().postDelayed(new Runnable() {
                            /*
                             * Showing splash screen with a timer. This will be useful when you
                             * want to show case your app logo / company
                             */
                            @Override
                            public void run() {
                                try {
                                    openAlertforMaterials();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, SPLASH_TIME_OUT);

                    }
                }

                b.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextorderBtn.setVisibility(View.VISIBLE);
                b.dismiss();
            }
        });

        addRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_material, null);
                alertbox.setView(dialogView);
                alertbox.setCancelable(false);
                final EditText room_name;
                final Button add, cancel;
                room_name = dialogView.findViewById(R.id.addText);
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
                            String addtexttype = room_name.getText().toString();

                            try {

                                result = new OrderHelper.AddRoom().execute(addtexttype.trim(), SharedPrefManager.getInstance(OrderCreate.this).getUser().access_token).get();

                                try {
                                    if (result.isEmpty()) {

                                        //   Toast.makeText(this, "Result empty", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Gson gson = new Gson();
                                        Toast.makeText(OrderCreate.this, "Room Add Successful", Toast.LENGTH_SHORT).show();


                                    }

                                } catch (Exception e) {

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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


    }

    private void openAlertforMaterials() {

        proceedButton.setVisibility(View.VISIBLE);
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.materialselectalert, null);
        alertbox.setView(dialogView);
        alertbox.setCancelable(false);
        final ImageButton addMaterialBtn = dialogView.findViewById(R.id.addButton);
        final Button proceed = dialogView.findViewById(R.id.ProceedBtn);
        final Button cancel = dialogView.findViewById(R.id.cancelBtn);

        materialmSwipeRefreshLayout = dialogView.findViewById(R.id.swipeRefreshLayouta);
        MaterialrecyclerView = dialogView.findViewById(R.id.recyclerView);
        MaterialrecyclerView.setHasFixedSize(true);
        MaterialrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final AlertDialog b = alertbox.create();
        b.show();

        materialmSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getmaterials();
            }
        });
        try {
            materialmSwipeRefreshLayout.setRefreshing(false);
            result = new OrderHelper.GetSubMaterialDetail().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {
                materialmSwipeRefreshLayout.setRefreshing(false);
            } else {
                materialmSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<RoomModel>>() {
                }.getType();
                RoomModels = new Gson().fromJson(result, listType);

                for (int i = 0; i < RoomModels.size(); i++) {
                    room_type.add(RoomModels.get(i).getSubMaterialType().toString());
                }
                Log.d("Error", RoomModels.toString());

                materialAdapter = new AlertMaterialAdapter(this, RoomModels);
                MaterialrecyclerView.setAdapter(materialAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        final AlertMaterialAdapter finalmaterialAdapter = materialAdapter;
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialIdSelected = "";
                List<RoomModel> stList = (finalmaterialAdapter).getRoomlist();

            /*    for(int i=0;i<RoomModels.size();i++) {
                    View view = MaterialrecyclerView.getChildAt(i);

                    CheckBox cb=view.findViewById(R.id.radioSelectRepair);
                    if(cb.isChecked())
                    {
                        TextView tv=view.findViewById(R.id.materialIDs);
                        idselect.add(String.valueOf(tv));

                    }
                  *//*  pricingView = stList.get(i);
                    if (pricingView.isSelected())
                        idselect.add(String.valueOf(pricingView.getSubMaterialID()));
                    //    materialIdSelected = materialIdSelected + pricingView.getSubMaterialID() + ",";
                    materialType = pricingView.getSubMaterialType();
                    Toast.makeText(OrderCreate.this, idselect.toString(), Toast.LENGTH_SHORT).show();*//*

                } catalogPost();

                b.dismiss();*/


                materialIdSelected = "";
                idselect.clear();
                for (int p = 0; p < stList.size(); p++) {
                    pricingView = stList.get(p);
                    if (pricingView.isSelected() == true) {
                        lebgth = p;
                        materialIdSelected = materialIdSelected + pricingView.getSubMaterialID() + ",";
                        idselect.add(String.valueOf(pricingView.getSubMaterialID()));
                        materialType = pricingView.getSubMaterialType();
                        Toast.makeText(OrderCreate.this, materialIdSelected, Toast.LENGTH_SHORT).show();

                    }

                    b.dismiss();
                }

                catalogPost();

                b.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b.dismiss();
            }
        });

        addMaterialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_material, null);
                alertbox.setView(dialogView);
                alertbox.setCancelable(false);
                final EditText room_name;
                final Button add, cancel;
                room_name = dialogView.findViewById(R.id.addText);
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
                            String addtexttype = room_name.getText().toString();

                            try {

                                result = new OrderHelper.PostSubMaterialMaster().execute("0", addtexttype.trim().toString()).get();
                                try {
                                    if (result.isEmpty()) {
                                    } else {
                                        Gson gson = new Gson();
                                        Toast.makeText(OrderCreate.this, "Material Successful", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            b.dismiss();
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.dismiss();
                        nextorderBtn.setVisibility(View.VISIBLE);


                    }
                });


            }
        });
    }

    private void catalogPost() {
        try {

            catalogId=0;
                result = new OrderHelper.PostCatalogDetails().execute(
                         roomIdSelected.toString().split(",")[0],
                        SharedPrefManager.getInstance(OrderCreate.this).getUser().userID).get();

                if(result.isEmpty()){

                }else {
                    Gson gson = new Gson();

                    Result jsonbody = gson.fromJson(result, Result.class);
                    if (jsonbody.getStatus() == true) {

                        catalogId = jsonbody.getCatalog_catagory_id();

                        try {
                            for (int j = 0; j < idselect.size(); j++) {
                                result = new OrderHelper.PostCatalogItemDetails().execute(
                                        "0", String.valueOf(catalogId), roomIdSelected.toString().split(",")[0], idselect.get(j),"","","","","","","","","","").get();

                                if (result.isEmpty()) {

                                } else {
                                    Gson gsons = new Gson();

                                    Result jsonbodys = gsons.fromJson(result, Result.class);
                                    if (jsonbody.getStatus() == true) {
                                        catalogItemID = jsonbodys.getCatalog_item_id();

                                    } else {
                                        Toast.makeText(OrderCreate.this, "Server Problem", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }


                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(OrderCreate.this, "Server Problem", Toast.LENGTH_SHORT).show();
                    }
                }



            openCatalogForMultipleMaterials(String.valueOf(catalogId));

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void openCatalogForMultipleMaterials(final String catalogIds) {

        proceedButton.setVisibility(View.VISIBLE);
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_catalog_update, null);
        alertbox.setView(dialogView);
        alertbox.setCancelable(false);

        final Button proceed, cancel;
        final  EditText afetrDiscountAmounts,tailorAmounts;
        proceed = dialogView.findViewById(R.id.ProceedBtn);
        cancel = dialogView.findViewById(R.id.cancelBtn);
        newCatalogForMultipleSwipeRefreshLayout = dialogView.findViewById(R.id.swipeRefreshLayoutass);
        newCatalogForMultipleMaterialrecyclerView = dialogView.findViewById(R.id.recyclerViewssss);
        newCatalogForMultipleMaterialrecyclerView.setHasFixedSize(true);
        newCatalogForMultipleMaterialrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        final AlertDialog b = alertbox.create();
        b.show();



        newCatalogForMultipleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCatalogs(catalogIds);
            }
        });
        getCatalogs(catalogIds);


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderItemPost(o_id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            /*
                             * Showing splash screen with a timer. This will be useful when you
                             * want to show case your app logo / company
                             */
                            @Override
                            public void run() {
                                try {
                                    catalogId = Long.parseLong(null);
                                    catalogItemID = Long.parseLong(null);
                                    roomIdSelected = "";
                                    oritemID = "";
                                    idselect.clear();
                                    GetCustomerFinalOrder(o_id.toString());

                                   // openAlertforRoom();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, SPLASH_TIME_OUT);
                    }
                });

                proceedButton.setVisibility(View.VISIBLE);
                b.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderItemPost(o_id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            /*
                             * Showing splash screen with a timer. This will be useful when you
                             * want to show case your app logo / company
                             */
                            @Override
                            public void run() {
                                try {
                                    catalogId = Long.parseLong(null);
                                    catalogItemID = Long.parseLong(null);
                                    roomIdSelected = "";
                                    oritemID = "";
                                    idselect.clear();
                                   // openAlertforRoom();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, SPLASH_TIME_OUT);
                    }
                });

                proceedButton.setVisibility(View.VISIBLE);
                b.dismiss();
            }
        });



    }

    private void getCatalogs(String catalogIds) {
        try {
            newCatalogForMultipleSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetCatlogMaterialtemList().execute(materialIdSelected.replace(",", "T"),catalogIds.toString()).get();
            if (result.isEmpty()) {
                newCatalogForMultipleSwipeRefreshLayout.setRefreshing(false);
            } else {
                newCatalogForMultipleSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CatalogCatagoryModel>>() {
                }.getType();
                catalogCatagoryModels = new Gson().fromJson(result, listType);
                Log.d("Error", catalogCatagoryModels.toString());
                catalogMaterilAdapter = new CatalogMaterilAdapter(this, catalogCatagoryModels,o_id);
                newCatalogForMultipleMaterialrecyclerView.setAdapter(catalogMaterilAdapter);



//                for (int i = 0; i < catalogCatagoryModels.size(); i++) {
//                    View view = newCatalogForMultipleMaterialrecyclerView.getChildAt(i);
//
//
//                    TextView textView1= (TextView) view.findViewById(R.id.catalog_item_idssss);
//                    String itemID=textView1.getText().toString();
//                    CatalogItemSID.add(itemID);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void getcatalogmaterials() {

        try {
            newCatalogForMultipleSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetCatlogMaterialtemList().execute(materialIdSelected.replace(",", "T")).get();
            if (result.isEmpty()) {
                newCatalogForMultipleSwipeRefreshLayout.setRefreshing(false);
            } else {
                newCatalogForMultipleSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CatalogCatagoryModel>>() {
                }.getType();
                catalogCatagoryModels = new Gson().fromJson(result, listType);
                Log.d("Error", catalogCatagoryModels.toString());
                catalogMaterilAdapter = new CatalogMaterilAdapter(this, catalogCatagoryModels,o_id);
                newCatalogForMultipleMaterialrecyclerView.setAdapter(catalogMaterilAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void openAlertforCatalogs() {

        proceedButton.setVisibility(View.VISIBLE);
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertcatalog, null);
        alertbox.setView(dialogView);
        alertbox.setCancelable(false);
        final AutoCompleteTextView datass;
        final Spinner measurementSpinner;
        final ImageButton addMeasurement = dialogView.findViewById(R.id.addButton);
        final EditText designName, designno, pageno, quantity, quality, mrp, discount;
        final Button proceed, cancel;
        final TextView afterDicountamt;

        proceed = dialogView.findViewById(R.id.ProceedBtn);
        cancel = dialogView.findViewById(R.id.cancelBtn);
        designName = dialogView.findViewById(R.id.designName);
        designno = dialogView.findViewById(R.id.catalog_design_no);
        pageno = dialogView.findViewById(R.id.catalog_page_no);
        quantity = dialogView.findViewById(R.id.catalog_quantity);
        quality = dialogView.findViewById(R.id.catalog_quality);
        mrp = dialogView.findViewById(R.id.catalog_mrp);
        discount = dialogView.findViewById(R.id.catalog_dicount);
        measurementSpinner = dialogView.findViewById(R.id.measurement_type);
        datass = dialogView.findViewById(R.id.measurmentDat);
        afterDicountamt = dialogView.findViewById(R.id.AfetrDiscountAmount);
        final AlertDialog b = alertbox.create();
        b.show();

        try {


            quantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    try {
                        if (quantity.getText().toString().length() == 0) {

                        } else {


                            Double measurmentData, discountAmounts, quantitys, mrpAmounts, calculate, total;

                            mrpAmounts = Double.parseDouble(mrp.getText().toString());
                            // discountAmounts = Double.parseDouble(discount.getText().toString());
                            //   measurmentData = Double.parseDouble(datass.getText().toString());
                            quantitys = Double.parseDouble(quantity.getText().toString());


                            calculate = mrpAmounts * quantitys;
                            total = calculate - (calculate) * (0 / 100);


                            int i = Math.round(Float.parseFloat(total.toString()));
                            afterDicountamt.setText(String.valueOf(i));


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        if (quantity.getText().toString().length() == 0) {

                        } else {


                            Double measurmentData, discountAmounts, quantitys, mrpAmounts, calculate, total;

                            mrpAmounts = Double.parseDouble(mrp.getText().toString());
                            discountAmounts = Double.parseDouble(discount.getText().toString());
                            //   measurmentData = Double.parseDouble(datass.getText().toString());
                            quantitys = Double.parseDouble(quantity.getText().toString());

                            calculate = mrpAmounts * quantitys;
                            total = calculate - (calculate) * (0 / 100);


                            int i = Math.round(Float.parseFloat(total.toString()));
                            afterDicountamt.setText(String.valueOf(i));


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            discount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    afterDicountamt.setText("");
                    try {
                        if (discount.getText().toString().length() == 0) {

                        } else {
                            if ((Integer.parseInt(discount.getText().toString().trim()) > 100)) {
                                discount.setError("wrong!");
                            } else {

                                Double measurmentData, discountAmounts, quantitys, mrpAmounts, calculate, total;

                                mrpAmounts = Double.parseDouble(mrp.getText().toString());
                                discountAmounts = Double.parseDouble(discount.getText().toString());
                                //   measurmentData = Double.parseDouble(datass.getText().toString());
                                quantitys = Double.parseDouble(quantity.getText().toString());


                                calculate = mrpAmounts * quantitys;
                                total = calculate - (calculate) * (discountAmounts / 100);


                                int i = Math.round(Float.parseFloat(total.toString()));
                                afterDicountamt.setText(String.valueOf(i));
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {
                    afterDicountamt.setText("");
                    try {
                        if (discount.getText().toString().length() == 0) {

                        } else {

                            if ((Integer.parseInt(discount.getText().toString().trim()) > 100)) {
                                discount.setError("wrong!");
                            } else {
                                Double measurmentData, discountAmounts, quantitys, mrpAmounts, calculate, total;

                                mrpAmounts = Double.parseDouble(mrp.getText().toString());
                                discountAmounts = Double.parseDouble(discount.getText().toString());
                                //   measurmentData = Double.parseDouble(datass.getText().toString());
                                quantitys = Double.parseDouble(quantity.getText().toString());

                                calculate = mrpAmounts * quantitys;
                                total = calculate - (calculate) * (discountAmounts / 100);


                                int i = Math.round(Float.parseFloat(total.toString()));
                                afterDicountamt.setText(String.valueOf(i));
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            result = new OrderHelper.GetMasterMeasurementDetails().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<MeasurementModel>>() {
                }.getType();

                measurementModelList = new Gson().fromJson(result, listType);
                uNames = new ArrayList<>();
                uIds = new ArrayList<>();
                for (MeasurementModel company : measurementModelList) {
                    uNames.add(company.getUmo_type());
                    uIds.add(String.valueOf(company.getUmo_id()));

                }
//                if(materialType.toString().equals("Wallpapers")){
//                    uNames.add("Roll");
//                    uIds.add(String.valueOf(4));
//                    //   measurementSpinner.setSelection(((ArrayAdapter<String>)measurementSpinner.getAdapter()).getPosition("Roll"));
//                }

                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, uNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                measurementSpinner.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        measurementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                measurementModel = measurementModelList.get(position);
                umo_id = measurementModel.getUmo_id();

//                for (int i=0;i>=measurementModelList.size();i++){
//                Roll = String.valueOf(measurementModelList.get(i).getUmo_type().equals(""));
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    result = new OrderHelper.PostCatalogDetails().execute(SharedPrefManager.getInstance(OrderCreate.this).getUser().access_token, designName.getText().toString(),
                            designno.getText().toString(), pageno.getText().toString(), quantity.getText().toString(),
                            mrp.getText().toString(), quality.getText().toString(), discount.getText().toString(), roomIdSelected.toString().split(",")[0],
                            "", materialIdSelected.toString().split(",")[0], "",
                            SharedPrefManager.getInstance(OrderCreate.this).getUser().userID, afterDicountamt.getText().toString()).get();
                    try {
                        if (result.isEmpty()) {
                            //Toast.makeText(OrderCreate.this, "Result empty", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();

                            Result jsonbody = gson.fromJson(result, Result.class);
                            if (jsonbody.getStatus() == true) {
                                catalogId = jsonbody.getCatalog_catagory_id();
                                OrderItemPost(o_id);
                                Toast.makeText(OrderCreate.this, jsonbody.getMessage(), Toast.LENGTH_SHORT).show();
                                //OrderPost();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new Handler().postDelayed(new Runnable() {
                                            /*
                                             * Showing splash screen with a timer. This will be useful when you
                                             * want to show case your app logo / company
                                             */
                                            @Override
                                            public void run() {
                                                try {
                                                    openAlertforRoom();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, SPLASH_TIME_OUT);
                                    }
                                });

                                proceedButton.setVisibility(View.VISIBLE);
                                b.dismiss();
                            } else {
                                Toast.makeText(OrderCreate.this, "Server Problem", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } catch (Exception e) {
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
                b.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextorderBtn.setVisibility(View.VISIBLE);
                try {

                    proceedButton.setVisibility(View.VISIBLE);
                    result = new OrderHelper.PostCatalogDetails().execute(SharedPrefManager.getInstance(OrderCreate.this).getUser().access_token, designName.getText().toString(),
                            designno.getText().toString(), pageno.getText().toString(), quantity.getText().toString(),
                            mrp.getText().toString(), quality.getText().toString(), discount.getText().toString(), roomIdSelected.toString().split(",")[0],
                            "", materialIdSelected.toString().split(",")[0], "",
                            SharedPrefManager.getInstance(OrderCreate.this).getUser().userID, afterDicountamt.getText().toString()).get();
                    try {
                        if (result.isEmpty()) {
                            //Toast.makeText(OrderCreate.this, "Result empty", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                Gson gson = new Gson();
                                Result jsonbody = gson.fromJson(result, Result.class);
                                if (jsonbody.getStatus() == true) {
                                    catalogId = jsonbody.getCatalog_catagory_id();
                                    Toast.makeText(OrderCreate.this, jsonbody.getMessage(), Toast.LENGTH_SHORT).show();
                                    //OrderPost();
                                    OrderItemPost(o_id);
                                    nextorderBtn.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(OrderCreate.this, "Server Problem", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                    } catch (Exception e) {
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

                b.dismiss();
            }
        });

        addMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_material, null);
                alertbox.setView(dialogView);
                alertbox.setCancelable(false);
                final EditText room_name;
                final Button add, cancel;
                room_name = dialogView.findViewById(R.id.addText);
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
                            String mname = room_name.getText().toString();
                            try {

                                result = new OrderHelper.PostMasterMeasurment().execute(mname.trim(), SharedPrefManager.getInstance(OrderCreate.this).getUser().access_token).get();

                                try {
                                    if (result.isEmpty()) {

                                    } else {

                                        Gson gson = new Gson();
                                        Toast.makeText(OrderCreate.this, "Measurement Add Successful", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            b.dismiss();
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // proceedButton.setVisibility(View.VISIBLE);
                        b.dismiss();


                    }
                });
            }
        });

    }

    private void OrderPost() {
        try {


            try {
                result = new OrderHelper.PostCustomerOrderDetails().execute(String.valueOf(order_id), setdate.getText().toString().trim(),
                        String.valueOf(customerId),
                        SharedPrefManager.getInstance(OrderCreate.this).getUser().userID).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            try {
                if (result.isEmpty()) {
                    //Toast.makeText(OrderCreate.this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        Gson gson = new Gson();

                        Result jsonbody = gson.fromJson(result, Result.class);
                        if (jsonbody.getStatus() == true) {
                            new Handler().postDelayed(new Runnable() {
                                /*
                                 * Showing splash screen with a timer. This will be useful when you
                                 * want to show case your app logo / company
                                 */
                                @Override
                                public void run() {
                                    try {
                                        openAlertforRoom();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, SPLASH_TIME_OUT);
                            o_id = String.valueOf(jsonbody.getOrderId());
                            //   Toast.makeText(OrderCreate.this, String.valueOf(jsonbody.getOrderId()), Toast.LENGTH_SHORT).show();
                            orderBtn.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(OrderCreate.this, "Server Problem", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void OrderItemPost(String oid) {
        try {
            proceedButton.setVisibility(View.VISIBLE);
            result = new OrderHelper.PostOrdersitemDetails().execute(String.valueOf(OrderItemID), oid,
                    roomIdSelected.toString().split(",")[0], materialIdSelected.toString().split(",")[0],
                    String.valueOf(catalogId)).get();
            try {
                if (result.isEmpty()) {
                    // Toast.makeText(OrderCreate.this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new Gson();

                    Result jsonbody = gson.fromJson(result, Result.class);
                    if (jsonbody.getStatus() == true) {
                        // openAlertforRoom();
                        oritemID = String.valueOf(jsonbody.getOrderItemID());
                        orderBtn.setVisibility(View.GONE);
                        nextorderBtn.setVisibility(View.VISIBLE);
                        proceedButton.setVisibility(View.VISIBLE);
                        try {

                      //  GetCustomerOrderModel(oid);

                        GetCustomerFinalOrder(oid);
                            proceedButton.setVisibility(View.VISIBLE);
                            Toast.makeText(OrderCreate.this, jsonbody.getMessage(), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(OrderCreate.this, "Server Problem", Toast.LENGTH_SHORT).show();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getRoomList() {

        try {
            roommSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetRoomsDetail().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {
                roommSwipeRefreshLayout.setRefreshing(false);
            } else {
                roommSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<RoomModel>>() {
                }.getType();
                RoomModels = new Gson().fromJson(result, listType);

                for (int i = 0; i < RoomModels.size(); i++) {
                    room_type.add(RoomModels.get(i).getRoom_type().toString());
                }
                Log.d("Error", RoomModels.toString());

                roomadapters = new RoomNewOrderAdapter(this, RoomModels);
                RoomsrecyclerView.setAdapter(roomadapters);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void getmaterials() {

        try {
            materialmSwipeRefreshLayout.setRefreshing(true);
            result = new OrderHelper.GetSubMaterialDetail().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {
                materialmSwipeRefreshLayout.setRefreshing(false);
            } else {
                materialmSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<RoomModel>>() {
                }.getType();
                RoomModels = new Gson().fromJson(result, listType);

                for (int i = 0; i < RoomModels.size(); i++) {
                    room_type.add(RoomModels.get(i).getSubMaterialType().toString());
                }
                Log.d("Error", RoomModels.toString());

                materialAdapter = new AlertMaterialAdapter(OrderCreate.this, RoomModels);
                MaterialrecyclerView.setAdapter(materialAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
