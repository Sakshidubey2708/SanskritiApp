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
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.modelnew.RoomModel;
import com.google.gson.Gson;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.OrderItemViewHolder> {

    private Context mCtx;
    private List<RoomModel> RoomModels;
    TextView calltext;
    private String result="";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    public RoomAdapter(Context mCtx, List<RoomModel> RoomModels) {
        this.mCtx = mCtx;
        this.RoomModels = RoomModels;
    }

    @NonNull
    @Override
    public RoomAdapter.OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.roomadapter_layout, null);
        return new RoomAdapter.OrderItemViewHolder(view, mCtx, RoomModels);
    }

    @Override
    public void onBindViewHolder(@NonNull final RoomAdapter.OrderItemViewHolder holder, int position) {
        RoomModel RoomModel = RoomModels.get(position);
        try {

            holder.room_name.setText(RoomModel.getRoom_type());
            holder.room_id.setText(String.valueOf(RoomModel.getRoom_catagory_id()));
            holder.deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(mCtx);
                    alertbox.setIcon(R.drawable.ic_warning_black_24dp);
                    alertbox.setTitle("Do You Want To Delete ?");
                    alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            deteleRoomApi(holder.room_id.getText().toString());
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
                    alertbox.setTitle("Add Room");
                    addtext.setText(holder.room_name.getText().toString().trim());
                    final AlertDialog b = alertbox.create();
                    b.show();


                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(buttonClick);
                            try {
                                result = new OrderHelper.PostUpdateRoom().execute(holder.room_id.getText().toString().trim(),addtext.getText().toString()).get();
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
    private void deteleRoomApi(String id) {
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
        return RoomModels.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private Context mCtx;
        private TextView room_name,room_id;
        private ImageButton  edit, deletebtn;

        private List<RoomModel> RoomModels;
        private RoomModel completeOrderModel;
        private long order_id = 0;
        private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        public OrderItemViewHolder(View view, Context mCtx, List<RoomModel> RoomModels) {
            super(view);
            this.mCtx = mCtx;
            this.RoomModels = RoomModels;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            room_name = view.findViewById(R.id.customerName);
             deletebtn=view.findViewById(R.id.deleteBtn);
            room_id = view.findViewById(R.id.orderID);
            edit = view.findViewById(R.id.editBtn);
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