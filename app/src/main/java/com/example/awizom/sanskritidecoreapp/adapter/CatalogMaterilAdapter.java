package com.example.awizom.sanskritidecoreapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.Result;
import com.example.awizom.sanskritidecoreapp.modelnew.CatalogCatagoryModel;
import com.example.awizom.sanskritidecoreapp.modelnew.KarigarModel;
import com.example.awizom.sanskritidecoreapp.modelnew.TailorcatagoryModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CatalogMaterilAdapter  extends RecyclerView.Adapter<CatalogMaterilAdapter.OrderItemViewHolder> {

    private Context mCtx;
    private List<CatalogCatagoryModel> CatalogCatagoryModels;
    TextView calltext;
    private String result="",id="";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private CheckBox selectedRadioButton;


    private TailorcatagoryModel tailorcatagoryModel;
    private List<TailorcatagoryModel> tailorcatagoryModelList;

    private KarigarModel karigarModel;
    private List<KarigarModel> karigarModelList;
    List<String> kid = new ArrayList<String>();
    List<String> kname = new ArrayList<String>();


    private TailorcatagoryModel tailorcatagorywrkModel;
    private List<TailorcatagoryModel> tailorcatagorywrkModelList;
    List<String> tId = new ArrayList<String>();
    List<String> tailorNames = new ArrayList<String>();
    List<String> tailorWorkId = new ArrayList<String>();
    List<String> tailorworkName = new ArrayList<String>();
    private long TailorCalculateID = 0, tid = 0, twid = 0,catalogItemID=0,karigarCalculateID=0,kID=0;
    String o_id="";

    public CatalogMaterilAdapter(Context mCtx, List<CatalogCatagoryModel> CatalogCatagoryModels,String o_id) {
        this.mCtx = mCtx;
        this.CatalogCatagoryModels = CatalogCatagoryModels;
        this.o_id = o_id;

    }



    @NonNull
    @Override
    public CatalogMaterilAdapter.OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.multipl_catalog_design_layout, null);
        return new CatalogMaterilAdapter.OrderItemViewHolder(view, mCtx, CatalogCatagoryModels);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatalogMaterilAdapter.OrderItemViewHolder holder, final int position) {
        CatalogCatagoryModel CatalogCatagoryModel = CatalogCatagoryModels.get(position);
        final int pos = position;
        try {


            holder.materil_id.setText(String.valueOf(CatalogCatagoryModel.getSubMaterialID()));
            holder.material_type.setText(CatalogCatagoryModel.getSubMaterialType());

            if(holder.material_type.getText().toString().equals("Sofa Fabric")){
                holder.addKarigar.setVisibility(View.VISIBLE);
                holder.addTailorCheckBox.setVisibility(View.GONE);
            }else {
                holder.addTailorCheckBox.setVisibility(View.VISIBLE);
                holder.addKarigar.setVisibility(View.GONE);
            }
            holder.mesurmentType.setText(CatalogCatagoryModel.getSubUMOType());
            holder.catalogIDs.setText(String.valueOf(CatalogCatagoryModel.getCatalog_catagory_id()));
            holder.catalogItemID.setText(String.valueOf(CatalogCatagoryModel.getCatalog_item_id()));
            holder.catalogQuantityNAme.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    try {
                        if (holder.catalogQuantityNAme.getText().toString().length() == 0) {

                        } else {


                            Double measurmentData, discountAmounts, quantitys, mrpAmounts, calculate, total;

                            mrpAmounts = Double.parseDouble(holder.CatalogMrpNames.getText().toString());
                            // discountAmounts = Double.parseDouble(discount.getText().toString());
                            //   measurmentData = Double.parseDouble(datass.getText().toString());
                            quantitys = Double.parseDouble(holder.catalogQuantityNAme.getText().toString());


                            calculate = mrpAmounts * quantitys;
                            total = calculate - (calculate) * (0 / 100);


                            int i = Math.round(Float.parseFloat(total.toString()));
                            holder.afetrDiscountAmount.setText(String.valueOf(i));


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {
                    holder.afetrDiscountAmount.setText("");
                    try {
                        if (holder.catalogQuantityNAme.getText().toString().length() == 0) {

                        } else {


                            Double measurmentData, discountAmounts, quantitys, mrpAmounts, calculate, total;

                            mrpAmounts = Double.parseDouble(holder.CatalogMrpNames.getText().toString());
                            // discountAmounts = Double.parseDouble(discount.getText().toString());
                            //   measurmentData = Double.parseDouble(datass.getText().toString());
                            quantitys = Double.parseDouble(holder.catalogQuantityNAme.getText().toString());


                            calculate = mrpAmounts * quantitys;
                            total = calculate - (calculate) * (0 / 100);


                            int i = Math.round(Float.parseFloat(total.toString()));
                            holder.afetrDiscountAmount.setText(String.valueOf(i));


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            holder.catalogdicountName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                   holder.afetrDiscountAmount.setText("");
                    try {
                        if (holder.catalogdicountName.getText().toString().length() == 0) {

                        } else {
                            if ((Integer.parseInt( holder.catalogdicountName.getText().toString().trim()) > 100)) {
                                holder.catalogdicountName.setError("wrong!");
                            } else {

                                Double measurmentData, discountAmounts, quantitys, mrpAmounts, calculate, total;

                                mrpAmounts = Double.parseDouble( holder.CatalogMrpNames.getText().toString());
                                discountAmounts = Double.parseDouble( holder.catalogdicountName.getText().toString());
                                //   measurmentData = Double.parseDouble(datass.getText().toString());
                                quantitys = Double.parseDouble( holder.catalogQuantityNAme.getText().toString());


                                calculate = mrpAmounts * quantitys;
                                total = calculate - (calculate) * (discountAmounts / 100);


                                int i = Math.round(Float.parseFloat(total.toString()));
                                holder.afetrDiscountAmount.setText(String.valueOf(i));
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {
                    holder.afetrDiscountAmount.setText("");
                    try {
                        if (holder.catalogdicountName.getText().toString().length() == 0) {

                        } else {
                            if ((Integer.parseInt( holder.catalogdicountName.getText().toString().trim()) > 100)) {
                                holder.catalogdicountName.setError("wrong!");
                            } else {

                                Double measurmentData, discountAmounts, quantitys, mrpAmounts, calculate, total;

                                mrpAmounts = Double.parseDouble( holder.CatalogMrpNames.getText().toString());
                                discountAmounts = Double.parseDouble( holder.catalogdicountName.getText().toString());
                                //   measurmentData = Double.parseDouble(datass.getText().toString());
                                quantitys = Double.parseDouble( holder.catalogQuantityNAme.getText().toString());


                                calculate = mrpAmounts * quantitys;
                                total = calculate - (calculate) * (discountAmounts / 100);


                                int i = Math.round(Float.parseFloat(total.toString()));
                                holder.afetrDiscountAmount.setText(String.valueOf(i));
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            holder.roomIds.setText(String.valueOf(CatalogCatagoryModel.getRoom_catagory_id()));

            holder.deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(mCtx);
                    alertbox.setIcon(R.drawable.ic_warning_black_24dp);
                    alertbox.setTitle("Do You Want To Delete ?");
                    alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            deteleRoomApi(holder.materil_id.getText().toString());
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
            });



            holder.addTailorCheckBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (holder.addTailorCheckBox.isChecked()) {
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(mCtx);
                        LayoutInflater inflater = LayoutInflater.from(mCtx);
                        final View dialogView = inflater.inflate(R.layout.activity_tailor_layout, null);
                        alertbox.setView(dialogView);
                        alertbox.setCancelable(false);
                        final Spinner tailorSpinnerName, tailorSpinnerWorking;
                        final EditText perPc_Amount, Total_Amount;
                        final AutoCompleteTextView pcsOf_Material;
                        final Button proceed, cancel;

                        proceed = dialogView.findViewById(R.id.ProceedBtn);
                        cancel = dialogView.findViewById(R.id.cancelBtn);

                        tailorSpinnerName = dialogView.findViewById(R.id.tailor_Names);
                        tailorSpinnerWorking = dialogView.findViewById(R.id.tailorWorking);
                        perPc_Amount = dialogView.findViewById(R.id.perPcAmount);
                        pcsOf_Material = dialogView.findViewById(R.id.pcsOfMaterial);
                        Total_Amount = dialogView.findViewById(R.id.TotalAmount);

                        final AlertDialog b = alertbox.create();
                        b.show();


                        try {

                            result = new OrderHelper.GetTailorsListDetails().execute(SharedPrefManager.getInstance(mCtx).getUser().access_token).get();

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

                                ArrayAdapter adapter = new ArrayAdapter(mCtx, android.R.layout.simple_spinner_item, tailorNames);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                tailorSpinnerName.setAdapter(adapter);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }


                        try {

                            result = new OrderHelper.GetTailorWorkingListDetails().execute(SharedPrefManager.getInstance(mCtx).getUser().access_token).get();

                            if (result.isEmpty()) {

                            } else {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<TailorcatagoryModel>>() {
                                }.getType();

                                tailorcatagorywrkModelList = new Gson().fromJson(result, listType);
                                tailorworkName = new ArrayList<>();
                                tailorWorkId = new ArrayList<>();
                                for (TailorcatagoryModel company : tailorcatagorywrkModelList) {
                                    tailorworkName.add(company.getTailorWorkType());
                                    tailorWorkId.add(String.valueOf(company.getTailorWorkID()));
                                }

                                ArrayAdapter adapter = new ArrayAdapter(mCtx, android.R.layout.simple_spinner_item, tailorworkName);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                tailorSpinnerWorking.setAdapter(adapter);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        tailorSpinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                tailorcatagoryModel = tailorcatagoryModelList.get(position);
                                tid = tailorcatagoryModel.getT_id();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                        tailorSpinnerWorking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                tailorcatagorywrkModel = tailorcatagorywrkModelList.get(position);
                                twid = tailorcatagorywrkModel.getTailorWorkID();


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                        pcsOf_Material.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                if (pcsOf_Material.getText().toString().length() == 0) {

                                } else {
                                    Double total, pcsMaterial, perAmount, calculate;

                                    perAmount = Double.parseDouble(perPc_Amount.getText().toString());
                                    pcsMaterial = Double.parseDouble(pcsOf_Material.getText().toString());
                                    calculate = perAmount * pcsMaterial;
                                    int i = Math.round(Float.parseFloat(calculate.toString()));
                                    Total_Amount.setText(String.valueOf(i) + ".00");

                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (pcsOf_Material.getText().toString().length() == 0) {

                                } else {
                                    Double total, pcsMaterial, perAmount, calculate;

                                    perAmount = Double.parseDouble(perPc_Amount.getText().toString());
                                    pcsMaterial = Double.parseDouble(pcsOf_Material.getText().toString());
                                    calculate = perAmount * pcsMaterial;
                                    int i = Math.round(Float.parseFloat(calculate.toString()));
                                    Total_Amount.setText(String.valueOf(i) + ".00");
                                    holder.TailorAmounts.setText(Total_Amount.getText().toString());

                                }

                            }
                        });


                        proceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {

                                   long TailorD=0;
                                    result = new OrderHelper.PostTailoCalculatio().execute(String.valueOf(TailorD), String.valueOf(twid),
                                            String.valueOf(tid), pcsOf_Material.getText().toString(), perPc_Amount.getText().toString(),
                                            Total_Amount.getText().toString(),o_id.toString()).get();
                                    try {
                                        if (result.isEmpty()) {

                                        } else {
                                            Gson gson = new Gson();

                                            Result jsonbody = gson.fromJson(result, Result.class);
                                            if (jsonbody.getStatus() == true) {
                                                holder.tailorAmountLayouts.setVisibility(View.VISIBLE);
                                                TailorCalculateID = jsonbody.getTailorCalculateID();
                                                perPc_Amount.setText("");
                                                pcsOf_Material.setText("");
                                                Total_Amount.setText("");
                                                Toast.makeText(mCtx, jsonbody.getMessage(), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(mCtx, jsonbody.getMessage(), Toast.LENGTH_SHORT).show();
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

                                holder.addTailorCheckBox.setChecked(false);
                                holder.tailorAmountLayouts.setVisibility(View.GONE);
                                b.dismiss();
                            }
                        });
                    } else {

                        Toast.makeText(mCtx, "error", Toast.LENGTH_SHORT).show();
                    }
                }
            });

     /*KARIGAR*/


            holder.addKarigar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (holder.addKarigar.isChecked()) {
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(mCtx);
                        LayoutInflater inflater = LayoutInflater.from(mCtx);
                        final View dialogView = inflater.inflate(R.layout.addkarigar_layout, null);
                        alertbox.setView(dialogView);
                        alertbox.setCancelable(false);
                        final Spinner karigarSpiinne;
                        final EditText perPc_Amount, Total_Amount;
                        final AutoCompleteTextView pcsOf_Material;
                        final Button proceed, cancel;

                        proceed = dialogView.findViewById(R.id.ProceedBtn);
                        cancel = dialogView.findViewById(R.id.cancelBtn);

                        karigarSpiinne = dialogView.findViewById(R.id.tailor_Names);
                        perPc_Amount = dialogView.findViewById(R.id.perPcAmount);
                        pcsOf_Material = dialogView.findViewById(R.id.pcsOfMaterial);
                        Total_Amount = dialogView.findViewById(R.id.TotalAmount);

                        final AlertDialog b = alertbox.create();
                        b.show();


                        try {

                            result = new OrderHelper.GetkarigarListDetails().execute(SharedPrefManager.getInstance(mCtx).getUser().access_token).get();

                            if (result.isEmpty()) {

                            } else {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<KarigarModel>>() {
                                }.getType();


                                karigarModelList = new Gson().fromJson(result, listType);
                                kname = new ArrayList<>();
                                kid = new ArrayList<>();
                                for (KarigarModel company : karigarModelList) {
                                    kname.add(company.getKarigarName());
                                    kid.add(String.valueOf(company.getKarigarID()));
                                }

                                ArrayAdapter adapter = new ArrayAdapter(mCtx, android.R.layout.simple_spinner_item, kname);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                karigarSpiinne.setAdapter(adapter);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }



                        karigarSpiinne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                karigarModel = karigarModelList.get(position);
                                kID = karigarModel.getKarigarID();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });

                        pcsOf_Material.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                if (pcsOf_Material.getText().toString().length() == 0) {

                                } else {
                                    Double total, pcsMaterial, perAmount, calculate;

                                    perAmount = Double.parseDouble(perPc_Amount.getText().toString());
                                    pcsMaterial = Double.parseDouble(pcsOf_Material.getText().toString());
                                    calculate = perAmount * pcsMaterial;
                                    int i = Math.round(Float.parseFloat(calculate.toString()));
                                    Total_Amount.setText(String.valueOf(i) + ".00");

                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (pcsOf_Material.getText().toString().length() == 0) {

                                } else {
                                    Double total, pcsMaterial, perAmount, calculate;

                                    perAmount = Double.parseDouble(perPc_Amount.getText().toString());
                                    pcsMaterial = Double.parseDouble(pcsOf_Material.getText().toString());
                                    calculate = perAmount * pcsMaterial;
                                    int i = Math.round(Float.parseFloat(calculate.toString()));
                                    Total_Amount.setText(String.valueOf(i) + ".00");

                                }

                            }
                        });


                        proceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    long karigarID=0;

                                    result = new OrderHelper.PostKarigarCalculate().execute(String.valueOf(karigarID),
                                            String.valueOf(kID), pcsOf_Material.getText().toString(), perPc_Amount.getText().toString(),
                                            Total_Amount.getText().toString(),o_id.toString()).get();
                                    try {
                                        if (result.isEmpty()) {

                                        } else {
                                            Gson gson = new Gson();

                                            Result jsonbody = gson.fromJson(result, Result.class);
                                            if (jsonbody.getStatus() == true) {
                                                karigarCalculateID = jsonbody.getKarigarCalculationID();
                                                perPc_Amount.setText("");
                                                pcsOf_Material.setText("");
                                                Total_Amount.setText("");
                                                Toast.makeText(mCtx, jsonbody.getMessage(), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(mCtx, jsonbody.getMessage(), Toast.LENGTH_SHORT).show();
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

                                holder.addKarigar.setChecked(false);
                                b.dismiss();
                            }
                        });
                    } else {

                        Toast.makeText(mCtx, "error", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            holder.submitButtons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {


                        result = new OrderHelper.PostCatalogItemDetails().execute(
                                holder.catalogItemID.getText().toString(),  holder.catalogIDs.getText().toString(),
                                holder.roomIds.getText().toString(),   holder.materil_id.getText().toString(),
                                String.valueOf(TailorCalculateID),holder.catalogDesignName.getText().toString(),"",holder.pageNoName.getText().toString(),
                                holder.catalogQuantityNAme.getText().toString(),holder.CatalogMrpNames.getText().toString(),holder.catalogQualityName.getText().toString(),
                                holder.catalogdicountName.getText().toString(),holder.afetrDiscountAmount.getText().toString(),String.valueOf(karigarCalculateID)
                        ).get();




                        if(result.isEmpty()){

                        }else {
                            Gson gson = new Gson();

                            Result jsonbody = gson.fromJson(result, Result.class);
                            if (jsonbody.getStatus() == true) {
                                Toast.makeText(mCtx, jsonbody.getMessage(), Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(mCtx, "Server Problem", Toast.LENGTH_SHORT).show();
                            }
                        }




                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }



                }
            });
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
    private void deteleRoomApi(String id) {


    }


    @Override
    public int getItemCount() {
        return CatalogCatagoryModels.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private Context mCtx;
        private TextView material_type,materil_id,mesurmentType,catalogIDs,catalogItemID,
                         catalogDesignName,catalogQualityName,pageNoName,CatalogMrpNames,roomIds,
                afetrDiscountAmount,TailorAmounts;
        private ImageButton edit, deletebtn;

        private LinearLayout tailorAmountLayouts;

        private AutoCompleteTextView  catalogQuantityNAme,catalogdicountName;

        private List<CatalogCatagoryModel> CatalogCatagoryModels;
        private CatalogCatagoryModel completeOrderModel,CatalogMrpName;
        private long order_id = 0;
        private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
        private CheckBox checkBox;
        private RadioButton radioButton;
        private Button submitButtons;
        public CheckBox addTailorCheckBox,addKarigar;


        public OrderItemViewHolder(View view, Context mCtx, List<CatalogCatagoryModel> CatalogCatagoryModels) {
            super(view);
            this.mCtx = mCtx;
            this.CatalogCatagoryModels = CatalogCatagoryModels;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            material_type = view.findViewById(R.id.SubMaterialTypeName);
            mesurmentType = view.findViewById(R.id.mesurmentType);
            materil_id = view.findViewById(R.id.matrilId);
            deletebtn=view.findViewById(R.id.closeButton);
            catalogIDs = view.findViewById(R.id.catalogID);
            submitButtons = view.findViewById(R.id.submitButton);
            catalogItemID = view.findViewById(R.id.catalog_item_idssss);
            tailorAmountLayouts = view.findViewById(R.id.tailorAmountLayout);
            TailorAmounts = view.findViewById(R.id.TailorAmount);

            catalogDesignName = view.findViewById(R.id.catalogDesignName);
            catalogQualityName = view.findViewById(R.id.catalogQuality);
            pageNoName = view.findViewById(R.id.pageNo);
            CatalogMrpNames = view.findViewById(R.id.CatalogMrp);
            afetrDiscountAmount = view.findViewById(R.id.AfetrDiscountAmount);

            catalogdicountName = view.findViewById(R.id.catalog_dicount);
            catalogQuantityNAme = view.findViewById(R.id.catalogQuantity);

            addTailorCheckBox = view.findViewById(R.id.tailorAddCheckbox);
            addKarigar = view.findViewById(R.id.karigarAdd);
            roomIds = view.findViewById(R.id.roomId);

        }

        @Override
        public void onClick(final View v) {
            v.startAnimation(buttonClick);

        }

        @Override
        public boolean onLongClick(View v) {

            return true;
        }
    }
    public List<CatalogCatagoryModel> getRoomlist() {
        return CatalogCatagoryModels;
    }
}


