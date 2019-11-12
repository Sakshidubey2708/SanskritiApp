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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.Result;
import com.example.awizom.sanskritidecoreapp.modelnew.CustomerOrderModel;
import com.example.awizom.sanskritidecoreapp.modelnew.TailorcatagoryModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProceedAmountAdapter extends RecyclerView.Adapter<ProceedAmountAdapter.OrderItemViewHolder> {

    private Context mCtx;
    private List<CustomerOrderModel> customerOrderModels;
    TextView calltext;
    private String result="";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private TailorcatagoryModel tailorcatagoryModel;
    private List<TailorcatagoryModel> tailorcatagoryModelList;

    private TailorcatagoryModel tailorcatagorywrkModel;
    private List<TailorcatagoryModel> tailorcatagorywrkModelList;
    List<String> tId = new ArrayList<String>();
    List<String> tailorNames = new ArrayList<String>();
    List<String> tailorWorkId = new ArrayList<String>();
    List<String> tailorworkName = new ArrayList<String>();
    private  long TailorCalculateID=0,tid=0,twid=0;


    public ProceedAmountAdapter(Context mCtx, List<CustomerOrderModel> CustomerOrderModels) {
        this.mCtx = mCtx;
        this.customerOrderModels = CustomerOrderModels;
    }

    @NonNull
    @Override
    public ProceedAmountAdapter.OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.proceed_amount_adapter, null);
        return new ProceedAmountAdapter.OrderItemViewHolder(view, mCtx, customerOrderModels);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProceedAmountAdapter.OrderItemViewHolder holder, final int position) {
        CustomerOrderModel customerModels = customerOrderModels.get(position);
        try {

            holder.orderId.setText(String.valueOf(customerModels.getOrderId()));
            holder.materialItemId.setText(String.valueOf(customerModels.getSubMaterialID()));
            holder.materialtype.setText(customerModels.getSubMaterialType());
            holder.catalogItemId.setText(String.valueOf(customerModels.getCatalog_catagory_id()));
            holder.orderItemId.setText(String.valueOf(customerModels.getOrderItemID()));
            holder.room_typeNAme.setText(customerModels.getRoom_type());
            holder.catalogDesign_names.setText(customerModels.getDesign());
            holder.quantities.setText(String.valueOf(customerModels.getQuantity()));
            holder.catalogMrp.setText(String.valueOf(customerModels.getMrp()) + "/-");
            holder.catalogDiscount.setText(String.valueOf(customerModels.getDiscount())+ "%");
            holder.orderItemId.setText(String.valueOf(customerModels.getOrderItemID()));
            holder.afterDiscountAmount.setText(String.valueOf(customerModels.getAfterDiscountAmt()));
            holder.quntity.setText(String.valueOf(customerModels.getQuantity()));

            holder.addTailorCheckBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if ( holder.addTailorCheckBox.isChecked()) {
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(mCtx);
                        LayoutInflater inflater = LayoutInflater.from(mCtx);
                        final View dialogView = inflater.inflate(R.layout.activity_tailor_layout, null);
                        alertbox.setView(dialogView);
                        alertbox.setCancelable(false);
                        final Spinner tailorSpinnerName,tailorSpinnerWorking;
                        final EditText perPc_Amount,Total_Amount;
                        final AutoCompleteTextView pcsOf_Material;
                        final Button proceed,cancel;

                        proceed   = dialogView.findViewById(R.id.ProceedBtn);
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
                                    Double total, pcsMaterial, perAmount,calculate;

                                    perAmount = Double.parseDouble(perPc_Amount.getText().toString());
                                    pcsMaterial = Double.parseDouble(pcsOf_Material.getText().toString());
                                    calculate = perAmount* pcsMaterial;
                                    int i = Math.round(Float.parseFloat(calculate.toString()));
                                    Total_Amount.setText(String.valueOf(i) + ".00");

                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (pcsOf_Material.getText().toString().length() == 0) {

                                } else {
                                    Double total, pcsMaterial, perAmount,calculate;

                                    perAmount = Double.parseDouble(perPc_Amount.getText().toString());
                                    pcsMaterial = Double.parseDouble(pcsOf_Material.getText().toString());
                                    calculate = perAmount* pcsMaterial;
                                    int i = Math.round(Float.parseFloat(calculate.toString()));
                                    Total_Amount.setText(String.valueOf(i) + ".00");

                                }

                            }
                        });


                        proceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {

                                    result = new OrderHelper.PostTailoCalculatio().execute(String.valueOf(TailorCalculateID), String.valueOf(twid),
                                            String.valueOf(tid), pcsOf_Material.getText().toString(), perPc_Amount.getText().toString(),
                                            Total_Amount.getText().toString(),holder.orderId.getText().toString()).get();
                                    try {
                                        if (result.isEmpty()) {

                                        } else {
                                            Gson gson = new Gson();

                                            Result jsonbody = gson.fromJson(result, Result.class);
                                            if(jsonbody.getStatus()==true){
                                                TailorCalculateID =  jsonbody.getCatalog_catagory_id();
                                                Toast.makeText(mCtx,jsonbody.getMessage(), Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(mCtx,jsonbody.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                    } catch (Exception e) { }

                                } catch (Exception e) {
                                    e.printStackTrace();

                                }
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                b.dismiss();
                            }
                        });
                    } else {

                        Toast.makeText(mCtx, "error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(mCtx);
                    alertbox.setIcon(R.drawable.ic_warning_black_24dp);
                    alertbox.setTitle("Do You Want To Delete ?");
                    alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {

                                result = new OrderHelper.PostOrderItemDelete().execute(holder.orderItemId.getText().toString()).get();
                                try {
                                    if (result.isEmpty()) {
                                        Toast.makeText(mCtx, "Result empty", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String itemLabel = String.valueOf(customerOrderModels.get(position));
                                        customerOrderModels.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position,customerOrderModels.size());
                                        Toast.makeText(mCtx, "Deleted", Toast.LENGTH_SHORT).show();


                                    }

                                } catch (Exception e) { }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
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

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    v.startAnimation(buttonClick);
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                    LayoutInflater inflater = LayoutInflater.from(mCtx);
                    final View dialogView = inflater.inflate(R.layout.add_material, null);
                    alertbox.setView(dialogView);
                    alertbox.setCancelable(false);
                    final EditText addtext;
                    final Button add,cancel;
                    addtext= dialogView.findViewById(R.id.addText);
                    add= dialogView.findViewById(R.id.addBtn);
                    cancel=dialogView.findViewById(R.id.backBtn);
                    alertbox.setTitle("Add Sub Material");
                    addtext.setText(holder.room_typeNAme.getText().toString().trim());
                    final AlertDialog b = alertbox.create();
                    b.show();


                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(buttonClick);
                            try {
                                result = new OrderHelper.PostSubMaterialMaster().execute(holder.room_id.getText().toString().trim(),addtext.getText().toString()).get();
                                if (result.isEmpty()) {

                                } else {
                                    Gson gson = new Gson();
                                    Toast.makeText(mCtx, "Update Successful", Toast.LENGTH_SHORT).show();
                                    addtext.setText(" ");
                                    b.dismiss();

                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(buttonClick);
                            b.dismiss();


                        }
                    });
                }
            });
        } catch (Exception E) {
            E.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return customerOrderModels.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private Context mCtx;
        private TextView room_typeNAme, room_id,catalogDesign_names,catalogMrp,afterDiscountAmount,
                materialtype,catalogDiscount,orderId,orderItemId,catalogItemId,materialItemId,quantities,meter,quntity;
        private ImageButton edit, deletebtn;
        private CheckBox addTailorCheckBox;

        private List<CustomerOrderModel> CustomerOrderModels;
        private CustomerOrderModel completeOrderModel;

        private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        public OrderItemViewHolder(View view, Context mCtx, List<CustomerOrderModel> CustomerOrderModels) {
            super(view);
            this.mCtx = mCtx;
            this.CustomerOrderModels = CustomerOrderModels;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            room_typeNAme = view.findViewById(R.id.roomTypes);
            catalogDesign_names=view.findViewById(R.id.catalogdesignName);
            catalogMrp = view.findViewById(R.id.catalogMrps);
            catalogDiscount = view.findViewById(R.id.catalog_Discount);
            deletebtn=view.findViewById(R.id.deltbtn);
            room_id = view.findViewById(R.id.roomid);
            edit = view.findViewById(R.id.editbtn);
            orderId = view.findViewById(R.id.orderId);
            orderItemId = view.findViewById(R.id.order_item_id);
            catalogItemId = view.findViewById(R.id.catalog_item_id);
            materialItemId = view.findViewById(R.id.material_item_id);
            addTailorCheckBox = view.findViewById(R.id.tailorAddCheckbox);
            materialtype = view.findViewById(R.id.materialtype);
            quantities = view.findViewById(R.id.quantity);
            meter = view.findViewById(R.id.meter);
            quntity = view.findViewById(R.id.quntity);
            afterDiscountAmount = view.findViewById(R.id.RESULTCALCULATEaFTERdISCOUNT);
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

    public List<CustomerOrderModel> getRoomlist() {
        return customerOrderModels;
    }
}

