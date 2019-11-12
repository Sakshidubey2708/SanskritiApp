package com.example.awizom.sanskritidecoreapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.modelnew.MeasurementModel;
import com.google.gson.Gson;

import java.util.List;

public class MesurementListAdapter extends RecyclerView.Adapter<MesurementListAdapter.OrderItemViewHolder> {

    private Context mCtx;
    private List<MeasurementModel> MeasurementModels;
    TextView calltext;
    Button callbtn;
    private String result="";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public MesurementListAdapter(Context mCtx, List<MeasurementModel> MeasurementModels) {
        this.mCtx = mCtx;
        this.MeasurementModels = MeasurementModels;
    }

    @NonNull
    @Override
    public MesurementListAdapter.OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.mesurement_list_adapter, null);
        return new MesurementListAdapter.OrderItemViewHolder(view, mCtx, MeasurementModels);
    }
    
    public void onBindViewHolder(@NonNull final OrderItemViewHolder holder, int position) {
        MeasurementModel MeasurementModel = MeasurementModels.get(position);
        try {

            holder.masterUMOName.setText(MeasurementModel.getUmo_type());
            holder.masterUMOId.setText(String.valueOf(MeasurementModel.getUmo_id()));
            holder.deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(mCtx);
                    alertbox.setIcon(R.drawable.ic_warning_black_24dp);
                    alertbox.setTitle("Do You Want To Delete ?");
                    alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            deteleMeasurementApi(holder.masterUMOId.getText().toString());
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
            holder.updatBtn.setOnClickListener(new View.OnClickListener() {
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
                    alertbox.setTitle("Update Measurement");
                    addtext.setText(holder.masterUMOName.getText().toString().trim());
                    final AlertDialog b = alertbox.create();
                    b.show();


                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(buttonClick);
                            try {
                                result = new OrderHelper.PostUpdateMasterMeasurment().execute(holder.masterUMOId.getText().toString().trim(),addtext.getText().toString()).get();
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
    private void deteleMeasurementApi(String id) {
//        try {
//
//            result=   new OrderHelper.DeleteTailor().execute(id).get();
//            try {
//                if (result.isEmpty()) {
//
////                    Toast.makeText(mCtx, "Server problem", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Gson gson = new Gson();
////                    Toast.makeText(mCtx, "Delete Successfully", Toast.LENGTH_SHORT).show();
//
//                }
//
//            } catch (Exception e) {
//
//                //  Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Toast.makeText(OrderDecorActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
//        }

    }

   

    @Override
    public int getItemCount() {
        return MeasurementModels.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private Context mCtx;
        private TextView masterUMOName, masterUMOId;
        private ImageButton callBtns, updatBtn,deletebtn;

        private List<MeasurementModel> MeasurementModels;
        private MeasurementModel completeOrderModel;
        private long order_id = 0;


        public OrderItemViewHolder(View view, Context mCtx, List<MeasurementModel> MeasurementModels) {
            super(view);
            this.mCtx = mCtx;
            this.MeasurementModels = MeasurementModels;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            masterUMOName = view.findViewById(R.id.customerName);
            masterUMOId = view.findViewById(R.id.orderID);
            callBtns = view.findViewById(R.id.callbtns);
            updatBtn = view.findViewById(R.id.editBtn);
            deletebtn=view.findViewById(R.id.deleteBtn);
        }

        @Override
        public void onClick(final View v) {


            switch (v.getId()){
                case R.id.editBtn:
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {

            return true;
        }
    }


}
