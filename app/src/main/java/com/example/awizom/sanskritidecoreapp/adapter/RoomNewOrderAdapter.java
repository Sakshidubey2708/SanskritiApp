package com.example.awizom.sanskritidecoreapp.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.modelnew.RoomModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class RoomNewOrderAdapter extends RecyclerView.Adapter<RoomNewOrderAdapter.OrderItemViewHolder> {

    private Context mCtx;
    private List<RoomModel> RoomModels;
    TextView calltext;
    private String result="";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private RadioButton selectedRadioButton;

    public RoomNewOrderAdapter(Context mCtx, List<RoomModel> RoomModels) {
        this.mCtx = mCtx;
        this.RoomModels = RoomModels;

    }

    @NonNull
    @Override
    public RoomNewOrderAdapter.OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.room_adapter, null);
        return new RoomNewOrderAdapter.OrderItemViewHolder(view, mCtx, RoomModels);
    }

    @Override
    public void onBindViewHolder(@NonNull final RoomNewOrderAdapter.OrderItemViewHolder holder, final int position) {
        RoomModel RoomModel = RoomModels.get(position);
        final int pos = position;


        try {

            holder.radioButton.setText(RoomModel.getRoom_type());
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
            holder.radioButton.setChecked(RoomModels.get(position).isSelected());
            holder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(RoomModel model: RoomModels)
                        model.setSelected(false);

                    RoomModels.get(position).setSelected(true);

                    // If current view (RadioButton) differs from previous selected radio button, then uncheck selectedRadioButton
                    if(null != selectedRadioButton && !v.equals(selectedRadioButton))
                        selectedRadioButton.setChecked(false);

                    // Replace the previous selected radio button with the current (clicked) one, and "check" it
                    selectedRadioButton = (RadioButton) v;
                    selectedRadioButton.setChecked(true);

//                    Toast.makeText(
//                            v.getContext(),
//                            "Clicked on Checkbox: " + holder.radioButton.getText() + " is "
//                                    + selectedRadioButton.isChecked(), Toast.LENGTH_LONG).show();

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

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mCtx, "Selected! ", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    private void openAlertforRoom() {

        AlertDialog.Builder alertbox = new AlertDialog.Builder(mCtx);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        final View dialogView = inflater.inflate(R.layout.list_room_order, null);
        alertbox.setView(dialogView);
        alertbox.setCancelable(false);
        final Button proceed = dialogView.findViewById(R.id.ProceedBtn);
        final Button cancel = dialogView.findViewById(R.id.cancelBtn);
        final SwipeRefreshLayout mSwipeRefreshLayout;
        final RecyclerView recyclerView;

        mSwipeRefreshLayout = dialogView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = dialogView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mCtx));

        final AlertDialog b = alertbox.create();
        b.show();

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b.dismiss();
            }
        });


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
        private RadioButton radioButton;

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
            radioButton = view.findViewById(R.id.radioSelectRepair);

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
    public List<RoomModel> getRoomlist() {
        return RoomModels;
    }

}