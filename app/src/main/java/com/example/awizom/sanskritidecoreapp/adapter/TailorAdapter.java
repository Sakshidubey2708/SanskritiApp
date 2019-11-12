package com.example.awizom.sanskritidecoreapp.adapter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.example.awizom.sanskritidecoreapp.modelnew.TailorcatagoryModel;
import com.google.gson.Gson;

import java.util.List;

public class TailorAdapter extends RecyclerView.Adapter<TailorAdapter.OrderItemViewHolder> {

    private Context mCtx;
    private List<TailorcatagoryModel> tailorcatagoryModels;
    TextView calltext;
    Button callbtn;
    private String result="";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    public TailorAdapter(Context mCtx, List<TailorcatagoryModel> tailorcatagoryModels) {
        this.mCtx = mCtx;
        this.tailorcatagoryModels = tailorcatagoryModels;
    }

    @NonNull
    @Override
    public TailorAdapter.OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.tailoradapter_layout, null);
        return new TailorAdapter.OrderItemViewHolder(view, mCtx, tailorcatagoryModels);
    }

    @Override
    public void onBindViewHolder(@NonNull final TailorAdapter.OrderItemViewHolder holder, int position) {
        TailorcatagoryModel TailorcatagoryModel = tailorcatagoryModels.get(position);
        try {

            holder.tailorName.setText(TailorcatagoryModel.getT_name());
            holder.tailorMobile.setText(TailorcatagoryModel.getT_contact());
            holder.tailorid.setText(String.valueOf(TailorcatagoryModel.getT_id()));
            holder.callBtns.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                    LayoutInflater inflater = LayoutInflater.from(mCtx);
                    final View dialogView = inflater.inflate(R.layout.calldialog, null);
                    alertbox.setView(dialogView);
                    calltext = dialogView.findViewById(R.id.textcall);

                    callbtn = dialogView.findViewById(R.id.callbtn);
                    alertbox.setTitle("Call Tailor");
                    calltext.setText(holder.tailorMobile.getText().toString());

                    callbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String phno = calltext.getText().toString();
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + phno));
                            if (ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            mCtx.startActivity(callIntent);

                        }
                    });

                    final AlertDialog b = alertbox.create();
                    b.show();
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
                            deteleTailorApi(holder.tailorid.getText().toString());
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


//            holder.updateButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    v.startAnimation(buttonClick);
//                    AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
//                    LayoutInflater inflater = LayoutInflater.from(mCtx);
//                    final View dialogView = inflater.inflate(R.layout.add_material, null);
//                    alertbox.setView(dialogView);
//                    final EditText addtext;
//                    final Button add,cancel;
//                    addtext= dialogView.findViewById(R.id.addText);
//                    add= dialogView.findViewById(R.id.addBtn);
//                    cancel=dialogView.findViewById(R.id.backBtn);
//                    alertbox.setTitle("Add Tailor");
//                    addtext.setText(holder.tailorName.getText().toString().trim());
//                    final AlertDialog b = alertbox.create();
//                    alertbox.setCancelable(false);
//                    b.show();
//
//
//                    add.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            v.startAnimation(buttonClick);
//                            try {
//                                result = new OrderHelper.PostUpdateRoom().execute(holder.tailorid.getText().toString().trim(),addtext.getText().toString(),"9876543223").get();
//                                if (result.isEmpty()) {
//
//                                } else {
//                                    Gson gson = new Gson();
//                                    Toast.makeText(mCtx, "Update Successful", Toast.LENGTH_SHORT).show();
//                                    addtext.setText(" ");
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//
//                            }
//
//                        }
//                    });
//                    cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            v.startAnimation(buttonClick);
//                            b.dismiss();
//
//
//                        }
//                    });
//                }
//            });
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    private void deteleTailorApi(String id) {
        try {

            result=   new OrderHelper.DeleteTailor().execute(id).get();
            try {
                if (result.isEmpty()) {

//                    Toast.makeText(mCtx, "Server problem", Toast.LENGTH_SHORT).show();
                } else {

                    Gson gson = new Gson();
//                    Toast.makeText(mCtx, "Delete Successfully", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {

                //  Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(OrderDecorActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return tailorcatagoryModels.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private Context mCtx;
        private TextView tailorName, tailorMobile, tailorid;
        private ImageButton callBtns, deletebtn,updateButton;

        private List<TailorcatagoryModel> tailorcatagoryModels;
        private TailorcatagoryModel completeOrderModel;
        private long order_id = 0;
        private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        public OrderItemViewHolder(View view, Context mCtx, List<TailorcatagoryModel> tailorcatagoryModels) {
            super(view);
            this.mCtx = mCtx;
            this.tailorcatagoryModels = tailorcatagoryModels;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tailorName = view.findViewById(R.id.customerName);
            tailorMobile = view.findViewById(R.id.customer_ontact);
            tailorid = view.findViewById(R.id.orderID);
            callBtns = view.findViewById(R.id.callbtns);
            deletebtn = view.findViewById(R.id.editBtn);
            updateButton = view.findViewById(R.id.editBtn);
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

}
