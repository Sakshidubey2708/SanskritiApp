package com.example.awizom.sanskritidecoreapp.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.adapter.MesurementListAdapter;
import com.example.awizom.sanskritidecoreapp.fragment.BottomOrderFragment;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.CustomerModel;
import com.example.awizom.sanskritidecoreapp.modelnew.CatalogCatagoryModel;
import com.example.awizom.sanskritidecoreapp.modelnew.MaterialCatagoryModel;
import com.example.awizom.sanskritidecoreapp.modelnew.MeasurementModel;
import com.example.awizom.sanskritidecoreapp.modelnew.OrderModel;
import com.example.awizom.sanskritidecoreapp.modelnew.OrderResponseModel;
import com.example.awizom.sanskritidecoreapp.modelnew.RoomModel;
import com.example.awizom.sanskritidecoreapp.modelnew.SubMAterialCatagoryModel;
import com.example.awizom.sanskritidecoreapp.modelnew.TailorcatagoryModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.awizom.sanskritidecoreapp.R.drawable.button_shape;
import static com.example.awizom.sanskritidecoreapp.R.drawable.colorchange;

public class OrderDecorActivity extends AppCompatActivity implements View.OnClickListener {

    EditText setdate, datass, tailorContact;
    Calendar myCalendar = Calendar.getInstance();
    ImageButton addRoom, addMaterial, addSubMaterial, addMesurment;
    private Button add, nextButton, cancel;
    private EditText room_name;
    private EditText addtext, catalogDesignNo, catalogPageNo, catalogQuantity, catalogQuality, catalogDiscount, catalogMRP;
    private TextView catalogDonebtn, measurement_btn, addTailorBtn;
    private AutoCompleteTextView customerName, catalogDesignName, tailorname;
    Double total, discount, grossAmount, multiply, sudhaAmount, measurmentAmount;
    private String addtexttype;
    String result = "";
    private CustomerModel dataOrderValue;
    private RoomModel roomModel;
    private List<RoomModel> roomModelList;
    ArrayList<String> room_type = new ArrayList<>();
    private CatalogCatagoryModel catalogCatagoryModel;
    private List<CatalogCatagoryModel> catagoryModelList;
    private MaterialCatagoryModel materialCatagoryModel;
    private List<MaterialCatagoryModel> materialCatagoryModelList;

    private TailorcatagoryModel tailorcatagoryModel;
    private List<TailorcatagoryModel> tailorcatagoryModelList;
    private LinearLayout tailorLayouts;

    private SubMAterialCatagoryModel subMAterialCatagoryModel;
    private List<SubMAterialCatagoryModel> subMAterialCatagoryModelList;
    private MeasurementModel measurementModel;
    private List<MeasurementModel> measurementModelList;
    private OrderModel orderModel;
    private long customerId = 0, catagoryId = 0, roomIds = 0, materialId = 0, materialSubId = 0, measurId = 0, orderId = 0, tid = 0, tailorid = 0, umo_id = 0;
    private List<CustomerModel> customerlist;
    private String[] customerNameList;
    private String[] roomNameList;
    private String[] roomNameID;
    private String[] catalogDesignNameList;
    private String[] materialNameList;
    private String[] subMaterialNameList;
    private String[] measurementNameList;
    private String[] measurementUMOIDList;
    private String[] tailorNameList;
    String orderid = "";
    ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    private CheckBox addTailorCheckBox;

    //TextView roomId;
    private AutoCompleteTextView roomNameSpinner, materialSpinner, subMaterialSpinner;//measurementSpinner
    private Spinner measurementSpinner, tailorSpinner;
    List<String> uIds = new ArrayList<String>();//add ids in this list
    List<String> uNames = new ArrayList<String>();//add names in this list
    List<String> tailorIDs = new ArrayList<String>();//add ids in this list
    List<String> tailorNames = new ArrayList<String>();//add na
    List<String> tId = new ArrayList<String>();//a
    private String custmerNames = "", mId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_decor);

        //Initialize all variables and events listeners
        initializePart();
    }

    @SuppressLint("ResourceAsColor")
    private void initializePart() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order Create");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(OrderDecorActivity.this, R.anim.slide_in, R.anim.slide_out);
                onBackPressed();
            }
        });

        toolbar.setSubtitleTextAppearance(OrderDecorActivity.this, R.style.styleA);
        toolbar.setTitleTextAppearance(OrderDecorActivity.this, R.style.styleA);
        toolbar.setTitleTextColor(Color.WHITE);
        custmerNames = getIntent().getStringExtra("CustomerName");
        addRoom = findViewById(R.id.addRoom);
        addMaterial = findViewById(R.id.addMaterial);
        addSubMaterial = findViewById(R.id.addSubMaterial);
        setdate = (EditText) findViewById(R.id.date);
        customerName = findViewById(R.id.customer_name);
        roomNameSpinner = findViewById(R.id.room_Name);
        //roomId = findViewById(R.id.room_Id);

        materialSpinner = findViewById(R.id.material_type);
        subMaterialSpinner = findViewById(R.id.sub_material_type);
        measurementSpinner = findViewById(R.id.measurement_type);
        tailorSpinner = findViewById(R.id.tailorSelectSpinner);
        nextButton = findViewById(R.id.order);
        nextButton.setOnClickListener(this);
        datass = findViewById(R.id.datas);

        addTailorCheckBox = findViewById(R.id.checkBoxNeedTailor);
        tailorLayouts = findViewById(R.id.tailorLayout);


        catalogDesignName = findViewById(R.id.design);
        catalogDesignNo = findViewById(R.id.catalog_design_no);
        catalogPageNo = findViewById(R.id.catalog_page_no);
        catalogQuantity = findViewById(R.id.catalog_quantity);
        catalogQuality = (EditText) findViewById(R.id.catalog_quality);
        catalogMRP = findViewById(R.id.catalog_mrp);
        catalogDiscount = findViewById(R.id.catalog_dicount);
        catalogDonebtn = findViewById(R.id.catalogDone);
        catalogDonebtn.setOnClickListener(this);

        tailorname = findViewById(R.id.tailor_Name);
        tailorContact = findViewById(R.id.tailor_contact);
        addTailorBtn = findViewById(R.id.tailor_add_btn);
        addTailorBtn.setOnClickListener(this);

        measurement_btn = findViewById(R.id.measurement_done_btn);
        measurement_btn.setOnClickListener(this);
        addMesurment = findViewById(R.id.addMesurmentBtn);
        addMesurment.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);


        try {
            GetCustomerDetailList();
            GetRoomDetails();
            GetCatalogDetails();
            GetMaterialDetails();
            GetSubMaterial();
            GetMeasurementDetails();
            GetTailorDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }


        String date_n = new SimpleDateFormat("yyyy/ MM/ dd", Locale.getDefault()).format(new Date());
        setdate.setText(date_n);

        try {
            customerName.setText(custmerNames.toString());
            GetCustomerName(customerName.getText().toString());
            if (customerName.getText().toString().isEmpty()) {
                customerName.setEnabled(true);
            } else {
                GetCustomerName(customerName.getText().toString());
                customerName.setEnabled(false);
                customerName.setTextColor(R.color.black);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(OrderDecorActivity.this, R.anim.slide_in, R.anim.slide_out);
                        onBackPressed();
                        startActivity(new Intent(OrderDecorActivity.this, HomeActivity.class));
                        finish();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        addTailorCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!addTailorCheckBox.isChecked()) {
                    tailorLayouts.setVisibility(View.GONE);
                } else {

                    tailorLayouts.setVisibility(View.VISIBLE);
                }
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
        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(OrderDecorActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        customerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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

        roomNameSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (roomNameSpinner.getText().length() == 0) {
                    roomIds = 0;
                } else {
                    GetRoomDetailsName(roomNameSpinner.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        catalogDesignName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (catalogDesignName.getText().length() == 0) {
                    catagoryId = 0;
                } else {
                    GetCatalogDesignname(catalogDesignName.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        materialSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (materialSpinner.getText().length() == 0) {
                    materialId = 0;
                } else {
                    GetMaterialDetailsName(materialSpinner.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        subMaterialSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (subMaterialSpinner.getText().length() == 0) {
                    materialSubId = 0;
                } else {
                    GetSubMaterialName(subMaterialSpinner.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        measurementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                measurementModel = measurementModelList.get(position);
                umo_id = measurementModel.getUmo_id();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        tailorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                tailorcatagoryModel = tailorcatagoryModelList.get(position);
                tid = tailorcatagoryModel.getT_id();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_material, null);
                alertbox.setView(dialogView);
                alertbox.setCancelable(false);
                addtext = dialogView.findViewById(R.id.addText);
                add = dialogView.findViewById(R.id.addBtn);
                cancel = dialogView.findViewById(R.id.backBtn);
                alertbox.setTitle("Add Room");
                final AlertDialog b = alertbox.create();
                b.show();

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (room_type.contains(addtext.getText().toString())) {
                            addtext.setError("All Ready Exist");
                            addtext.requestFocus();
                        } else if (addtext.getText().toString().isEmpty()) {
                            addtext.setError("Required !");
                        } else {
                            postAddRoom();
                            b.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetRoomDetails();
                        b.dismiss();


                    }
                });

                GetRoomDetails();
                progressDialog.dismiss();
            }
        });


        addMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_material, null);
                alertbox.setView(dialogView);
                alertbox.setCancelable(false);
                addtext = dialogView.findViewById(R.id.addText);
                add = dialogView.findViewById(R.id.addBtn);
                cancel = dialogView.findViewById(R.id.backBtn);
                alertbox.setTitle("Add Material");
                final AlertDialog b = alertbox.create();
                b.show();


                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (addtext.getText().toString().isEmpty()) {
                            addtext.setError("Required !");
                        } else {
                            postAddMterial();
                            b.dismiss();
                        }


                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetMaterialDetails();
                        b.dismiss();


                    }
                });
                GetMaterialDetails();

            }
        });

        addSubMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_material, null);
                alertbox.setView(dialogView);
                alertbox.setCancelable(false);
                addtext = dialogView.findViewById(R.id.addText);
                add = dialogView.findViewById(R.id.addBtn);

                cancel = dialogView.findViewById(R.id.backBtn);
                alertbox.setTitle("Add Sub Material");
                final AlertDialog b = alertbox.create();
                b.show();


                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (room_type.contains(addtext.getText().toString())) {
                            addtext.setError("All Ready Exist");
                            addtext.requestFocus();
                        } else if (addtext.getText().toString().isEmpty()) {
                            addtext.setError("Required !");
                        } else {
                            postAddSubMaterial();
                            b.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetSubMaterial();
                        b.dismiss();


                    }
                });

                GetSubMaterial();


            }
        });


        tailorname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tailorname.getText().length() == 0) {
                    tailorid = 0;
                } else {
                    //   GetTailorDetailsName(tailorname.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.catalogDone:
                try {
                    if (catalogDesignName.getText().toString().isEmpty() || catalogDesignNo.getText().toString().isEmpty()
                            || catalogPageNo.getText().toString().isEmpty() || catalogQuantity.getText().toString().isEmpty()
                            || catalogMRP.getText().toString().isEmpty()) {

                        catalogDesignName.setError("Required!");
                        catalogDesignNo.setError("Required!");
                        catalogPageNo.setError("Required!");
                        catalogQuantity.setError("Required!");
                        catalogMRP.setError("Required!");
                        catalogDiscount.setError("Required!");
                    } else {

                        if ((Integer.parseInt(catalogDiscount.getText().toString().trim()) > 100)) {
                            catalogDiscount.setError("wrong!");
                        } else {
                            catalogDonebtn.setBackgroundColor(0xff99cc00);
                            catalogDonebtn.setEnabled(false);
                            PostCatalogDetails();
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.order:
                try {
                    if ((customerName.getText().toString().isEmpty()) || roomNameSpinner.getText().toString().isEmpty() ||
                            catalogDesignName.getText().toString().isEmpty() || materialSpinner.getText().toString().isEmpty()
                            || subMaterialSpinner.getText().toString().isEmpty()) {// || measurementSpinner.getText().toString().isEmpty()
                        customerName.setError("Required!");
                        roomNameSpinner.setError("Required!");
                        catalogDesignName.setError("Required!");
                        materialSpinner.setError("Required!");
                        subMaterialSpinner.setError("Required!");
                        // measurementSpinner.setError("Required!");

                    } else {
                        PostOrderDetails();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.measurement_done_btn:
                try {
                    if (datass.getText().toString().isEmpty()) {
                        datass.setError("Required!");
                    } else {
                        total = Double.parseDouble(catalogMRP.getText().toString());
                        discount = Double.parseDouble(catalogDiscount.getText().toString());
                        multiply = Double.parseDouble(catalogMRP.getText().toString()) * Double.parseDouble(catalogQuantity.getText().toString()) * Double.parseDouble(datass.getText().toString());
                        measurmentAmount = Double.parseDouble(datass.getText().toString());
                        grossAmount = multiply - (multiply) * (discount / 100);
                        sudhaAmount = grossAmount;
                        postMeasurment();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.tailor_add_btn:
                try {
                    if (tailorname.getText().toString().isEmpty() || tailorContact.getText().toString().isEmpty()) {
                        tailorname.setError("Required!");
                        tailorContact.setError("Required!");
                    } else {
                        posttailor();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;


        }
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        setdate.setText(sdf.format(myCalendar.getTime()));
    }

    //These POST Api Calling methods
    private void postAddRoom() {
        addtexttype = addtext.getText().toString();

        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            result = new OrderHelper.AddRoom().execute(addtexttype.trim(), SharedPrefManager.getInstance(OrderDecorActivity.this).getUser().access_token).get();
            progressDialog.dismiss();
            try {
                if (result.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {

                    Gson gson = new Gson();
                    Toast.makeText(this, "Room Add Successful", Toast.LENGTH_SHORT).show();
                    addtext.setText(" ");
                    GetRoomDetails();


                }

            } catch (Exception e) {

//                Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            //      Toast.makeText(OrderDecorActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }


    }

    private void postAddMterial() {
        addtexttype = addtext.getText().toString();

        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            result = new OrderHelper.AddMaterial().execute(addtexttype.trim(), SharedPrefManager.getInstance(OrderDecorActivity.this).getUser().access_token, String.valueOf(catagoryId)).get();
            progressDialog.dismiss();
            try {
                if (result.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    Toast.makeText(this, "Material Add Successful", Toast.LENGTH_SHORT).show();
                    addtext.setText(" ");
                    GetMaterialDetails();
                }

            } catch (Exception e) {

                //  Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
                //
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(OrderDecorActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }


    }

    private void postAddSubMaterial() {
        addtexttype = addtext.getText().toString();

        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            result = new OrderHelper.AddSubMaterial().execute(addtexttype.trim(), SharedPrefManager.getInstance(OrderDecorActivity.this).getUser().access_token, String.valueOf(materialId)).get();
            progressDialog.dismiss();
            try {
                if (result.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    Toast.makeText(this, "Sub Material Add Successful", Toast.LENGTH_SHORT).show();
                    addtext.setText(" ");
                    GetSubMaterial();

                }

            } catch (Exception e) {

                //Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(OrderDecorActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void postMeasurment() {

        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            result = new OrderHelper.PostMeasurment().execute(String.valueOf(umo_id), String.valueOf(materialSubId), datass.getText().toString(),
                    SharedPrefManager.getInstance(OrderDecorActivity.this).getUser().userID).get();

            try {
                if (result.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    OrderResponseModel responseModel = gson.fromJson(result, OrderResponseModel.class);
                    if (responseModel != null) {
                        mId = String.valueOf(responseModel.getOrderid());
                        if (mId.toString() == null) {
                            mId = "0";
                        }
                        Toast.makeText(this, "Detail Fill", Toast.LENGTH_SHORT).show();
                        measurement_btn.setBackgroundColor(0xff99cc00);
                        measurement_btn.setEnabled(false);
                    }


                }

            } catch (Exception e) {

                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PostCatalogDetails() {

        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            result = new OrderHelper.PostCatalogDetails().execute(SharedPrefManager.getInstance(OrderDecorActivity.this).getUser().access_token, catalogDesignName.getText().toString(),
                    catalogDesignNo.getText().toString(), catalogPageNo.getText().toString(), catalogQuantity.getText().toString(),
                    catalogMRP.getText().toString(), catalogQuality.getText().toString(), catalogDiscount.getText().toString(), String.valueOf(roomIds)).get();


            try {
                if (result.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    Toast.makeText(this, "Detail Fill", Toast.LENGTH_SHORT).show();
                    GetCatalogDesignname(catalogDesignName.getText().toString());
                }

            } catch (Exception e) {

//                Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void posttailor() {

        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            result = new OrderHelper.PostTailor().execute(SharedPrefManager.getInstance(this).getUser().access_token,
                    tailorname.getText().toString().trim(), tailorContact.getText().toString()).get();

            try {
                if (result.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    addTailorBtn.setBackgroundColor(0xff99cc00);
                    addTailorBtn.setEnabled(false);
                    Gson gson = new Gson();
                    Toast.makeText(this, "Detail Fill", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {

                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            //   Toast.makeText(OrderDecorActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    //These GET Api Calling methods
    private void GetCustomerDetailList() {
        try {

            result = new OrderHelper.GetAllCustomerDetails().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {

            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CustomerModel>>() {
                }.getType();
                customerlist = new Gson().fromJson(result, listType);
                customerNameList = new String[customerlist.size()];
                for (int i = 0; i < customerlist.size(); i++) {
                    customerNameList[i] = String.valueOf(customerlist.get(i).getCustomerName());
                }

                adapter = new ArrayAdapter<String>(OrderDecorActivity.this, android.R.layout.select_dialog_item, customerNameList);
                customerName.setThreshold(1);//will start working from first character
                customerName.setAdapter(adapter);//setting the adapter materialIdSelected into the AutoCompleteTextView


            }
        } catch (Exception e) {
            e.printStackTrace();
            //  Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void GetCustomerName(String customerNames) {
        try {

            result = new OrderHelper.GetCustomerNames().execute(SharedPrefManager.getInstance(OrderDecorActivity.this).getUser().access_token, customerNames).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<CustomerModel>() {
                }.getType();
                dataOrderValue = new Gson().fromJson(result, listType);
                if (dataOrderValue != null) {
                    customerId = dataOrderValue.getCustomerID();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //     Toast.makeText(OrderDecorActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void GetRoomDetails() {
        try {

            result = new OrderHelper.GetRoomsDetail().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {

            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<RoomModel>>() {
                }.getType();
                roomModelList = new Gson().fromJson(result, listType);
                roomNameList = new String[roomModelList.size()];
                roomNameID = new String[roomModelList.size()];
                for (int i = 0; i < roomModelList.size(); i++) {
                    roomNameList[i] = String.valueOf(roomModelList.get(i).getRoom_type());
                    roomNameID[i] = String.valueOf(roomModelList.get(i).getRoom_catagory_id());
                    room_type.add(roomModelList.get(i).getRoom_type().toString());

                }
                for (String s : roomNameID) {
                    //  roomId.setText(s);
                }
                adapter = new ArrayAdapter<String>(OrderDecorActivity.this, android.R.layout.select_dialog_item, roomNameList);
                roomNameSpinner.setThreshold(1);//will start working from first character
                roomNameSpinner.setAdapter(adapter);//setting the adapter materialIdSelected into the AutoCompleteTextView

            }
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void GetRoomDetailsName(String room_types) {
        try {

            result = new OrderHelper.GetRoomsDetailNames().execute(SharedPrefManager.getInstance(OrderDecorActivity.this).getUser().access_token, room_types).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<RoomModel>() {
                }.getType();
                roomModel = new Gson().fromJson(result, listType);
                if (roomModel != null) {
                    roomIds = roomModel.getRoom_catagory_id();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(OrderDecorActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void GetMaterialDetails() {
        try {

            result = new OrderHelper.GetMaterialsDetail().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {

            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<MaterialCatagoryModel>>() {
                }.getType();
                materialCatagoryModelList = new Gson().fromJson(result, listType);
                materialNameList = new String[materialCatagoryModelList.size()];
                for (int i = 0; i < materialCatagoryModelList.size(); i++) {
                    materialNameList[i] = String.valueOf(materialCatagoryModelList.get(i).getMcatagory_type());
                }

                adapter = new ArrayAdapter<String>(OrderDecorActivity.this, android.R.layout.select_dialog_item, materialNameList);
                materialSpinner.setThreshold(1);//will start working from first character
                materialSpinner.setAdapter(adapter);//setting the adapter materialIdSelected into the AutoCompleteTextView

            }
        } catch (Exception e) {
            e.printStackTrace();
            //    Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void GetMaterialDetailsName(String mcatagory_type) {
        try {

            result = new OrderHelper.GetMaterialsDetailNames().execute(SharedPrefManager.getInstance(OrderDecorActivity.this).getUser().access_token, mcatagory_type).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<MaterialCatagoryModel>() {
                }.getType();
                materialCatagoryModel = new Gson().fromJson(result, listType);
                if (materialCatagoryModel != null) {
                    materialId = materialCatagoryModel.getMcatagory_id();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  Toast.makeText(OrderDecorActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void GetSubMaterial() {
        try {

            result = new OrderHelper.GetSubMaterialsDetail().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {

            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<SubMAterialCatagoryModel>>() {
                }.getType();
                subMAterialCatagoryModelList = new Gson().fromJson(result, listType);
                subMaterialNameList = new String[subMAterialCatagoryModelList.size()];
                for (int i = 0; i < subMAterialCatagoryModelList.size(); i++) {
                    subMaterialNameList[i] = String.valueOf(subMAterialCatagoryModelList.get(i).getMsubcatagory_type());
                }

                adapter = new ArrayAdapter<String>(OrderDecorActivity.this, android.R.layout.select_dialog_item, subMaterialNameList);
                subMaterialSpinner.setThreshold(1);//will start working from first character
                subMaterialSpinner.setAdapter(adapter);//setting the adapter materialIdSelected into the AutoCompleteTextView


            }
        } catch (Exception e) {
            e.printStackTrace();
            //  Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void GetSubMaterialName(String mcatagory_type) {
        try {

            result = new OrderHelper.GetSubMaterialsDetailNames().execute(SharedPrefManager.getInstance(OrderDecorActivity.this).getUser().access_token, mcatagory_type).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<SubMAterialCatagoryModel>() {
                }.getType();
                subMAterialCatagoryModel = new Gson().fromJson(result, listType);
                if (subMAterialCatagoryModel != null) {
                    materialSubId = subMAterialCatagoryModel.getMsubcatagory_id();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //   Toast.makeText(OrderDecorActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void GetCatalogDetails() {
        try {

            result = new OrderHelper.GetCatalogsDetail().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {


            } else {

                Gson gson = new Gson();
                Type listType = new TypeToken<List<CatalogCatagoryModel>>() {
                }.getType();
                catagoryModelList = new Gson().fromJson(result, listType);
                catalogDesignNameList = new String[catagoryModelList.size()];
                for (int i = 0; i < catagoryModelList.size(); i++) {
                    catalogDesignNameList[i] = String.valueOf(catagoryModelList.get(i).getDesign());
                }

                adapter = new ArrayAdapter<String>(OrderDecorActivity.this, android.R.layout.select_dialog_item, catalogDesignNameList);
                catalogDesignName.setThreshold(1);//will start working from first character
                catalogDesignName.setAdapter(adapter);//setting the adapter materialIdSelected into the AutoCompleteTextView

            }
        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void GetCatalogDesignname(String catalogsDesignName) {
        try {

            result = new OrderHelper.GetCatalogsDesignName().execute(SharedPrefManager.getInstance(this).getUser().access_token, catalogsDesignName).get();
            if (result.isEmpty()) {


            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<CatalogCatagoryModel>() {
                }.getType();
                catalogCatagoryModel = new Gson().fromJson(result, listType);
                if (catalogCatagoryModel != null) {
                    catagoryId = catalogCatagoryModel.getCatalog_catagory_id();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void GetMeasurementDetails() {
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

                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, uNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                measurementSpinner.setAdapter(adapter);
//                    measurementNameList = new String[measurementModelList.size()];
//                    measurementUMOIDList = new String[measurementModelList.size()];
//                    for (int i = 0; i < measurementModelList.size(); i++) {
//                        measurementNameList[i] = String.valueOf(measurementModelList.get(i).getUmo_type());
//                        measurementUMOIDList[i] = String.valueOf(measurementModelList.get(i).getUmo_id());
//                    }
//                    ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(OrderDecorActivity.this, R.layout.spinner_text, measurementNameList );
//                    langAdapter.setDropDownViewResource(R.layout.create_simple_spinner_dropdown);
//                    measurementSpinner.setAdapter(langAdapter);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void GetMeasurementDetailsName(String mcatagory_type) {
        try {

            result = new OrderHelper.GetMeasurementDetailsName().execute(SharedPrefManager.getInstance(OrderDecorActivity.this).getUser().access_token, mcatagory_type).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<MeasurementModel>() {
                }.getType();
                measurementModel = new Gson().fromJson(result, listType);
                if (measurementModel != null) {
                    //measurId = measurementModel.getMeasur_id();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void GetTailorDetailsName(String tailorName) {
        try {

            result = new OrderHelper.GetTailorsDetailNames().execute(SharedPrefManager.getInstance(this).getUser().access_token, tailorName).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<TailorcatagoryModel>() {
                }.getType();
                tailorcatagoryModel = new Gson().fromJson(result, listType);
                if (tailorcatagoryModel != null) {
                    tailorid = tailorcatagoryModel.getT_id();
                    tailorContact.setText(tailorcatagoryModel.getT_contact());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  Toast.makeText(OrderDecorActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void GetTailorDetails() {
        try {

//            result = new OrderHelper.GetTailorsDetails().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();

            result = new OrderHelper.GetTailorsListDetails().execute(SharedPrefManager.getInstance(this).getUser().access_token).get();

            if (result.isEmpty()) {

            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<TailorcatagoryModel>>() {
                }.getType();


                tailorcatagoryModelList = new Gson().fromJson(result, listType);
                tailorNames = new ArrayList<>();
                tId = new ArrayList<>();
                for (TailorcatagoryModel company : tailorcatagoryModelList) {
                    tailorNames.add(company.getT_name());
                    tId.add(String.valueOf(company.getT_id()));
                }

                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tailorNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tailorSpinner.setAdapter(adapter);

//
//                tailorcatagoryModelList = new Gson().fromJson(result, listType);
//                tailorNameList = new String[tailorcatagoryModelList.size()];
//                tailorNames = new ArrayList<>();
//
//                for (int i = 0; i < tailorcatagoryModelList.size(); i++) {
//                    tailorNameList[i] = String.valueOf(tailorcatagoryModelList.get(i).getT_name());
//                }
//
//                adapter = new ArrayAdapter<String>(OrderDecorActivity.this, android.R.layout.select_dialog_item, tailorNameList);
//                tailorname.setThreshold(1);//will start working from first character
//                tailorname.setAdapter(adapter);//setting the adapter materialIdSelected into the AutoCompleteTextView

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void PostOrderDetails() {

        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            String Tid = "";
            if (tid == 0) {
                Tid = String.valueOf(20024);
            } else {
                Tid = String.valueOf(tid);
            }

            result = new OrderHelper.PostOrderDetails().execute(SharedPrefManager.getInstance(OrderDecorActivity.this).getUser().access_token,
                    String.valueOf(customerId), setdate.getText().toString().trim(), roomNameSpinner.getText().toString(),
                    SharedPrefManager.getInstance(OrderDecorActivity.this).getUser().userID,
                    Tid, String.valueOf(catagoryId), mId).get();


            try {
                if (result.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    OrderResponseModel orderModel = gson.fromJson(result, OrderResponseModel.class);
                    if (orderModel != null) {
                        String orderid = String.valueOf(orderModel.getOrderid());
                        if (orderid.toString() == null) {
                            orderid = "0";
                        }
                        Intent intent = new Intent(OrderDecorActivity.this, AmountOrderActivity.class);
                        intent = intent.putExtra("ORDERID", orderid);
                        intent = intent.putExtra("CustomerName", customerName.getText().toString());
                        intent = intent.putExtra("MRP", multiply);
                        intent = intent.putExtra("Discount", catalogDiscount.getText().toString());
                        intent = intent.putExtra("GrossAmount", sudhaAmount);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }


                }

            } catch (Exception e) {


            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

