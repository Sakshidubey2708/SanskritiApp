package com.example.awizom.sanskritidecoreapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.awizom.sanskritidecoreapp.modelnew.CustomerOrderModel;
import com.example.awizom.sanskritidecoreapp.modelnew.RoomModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class OrderRoomAdapter extends RecyclerView.Adapter<OrderRoomAdapter.OrderItemViewHolder> {

    private Context mCtx;
    private List<CustomerOrderModel> RoomModels;
    TextView calltext;
    private String result="";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    public OrderRoomAdapter(Context mCtx, List<CustomerOrderModel> RoomModels) {
        this.mCtx = mCtx;
        this.RoomModels = RoomModels;
    }

    @NonNull
    @Override
    public OrderRoomAdapter.OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.listroom, null);
        return new OrderRoomAdapter.OrderItemViewHolder(view, mCtx, RoomModels);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderRoomAdapter.OrderItemViewHolder holder, int position) {
        CustomerOrderModel RoomModel = RoomModels.get(position);
        try {

            holder.room_name.setText(RoomModel.getRoom_type());
            holder.orderId.setText(String.valueOf(RoomModel.getOrderId()));
            holder.roomId.setText(String.valueOf(RoomModel.getOrderItemID()));
            holder.room_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertbox = new AlertDialog.Builder(mCtx);
                    LayoutInflater inflater = LayoutInflater.from(mCtx);
                    final View dialogView = inflater.inflate(R.layout.openview, null);
                    alertbox.setView(dialogView);
                    alertbox.setCancelable(false);
                    SwipeRefreshLayout materialmSwipeRefreshLayout = dialogView.findViewById(R.id.swipeRefreshLayouta);
                    RecyclerView  MaterialrecyclerView = dialogView.findViewById(R.id.recyclerView);
                    MaterialrecyclerView.setHasFixedSize(true);
                    MaterialrecyclerView.setLayoutManager(new LinearLayoutManager(mCtx));

                    final Button  ok;
                    ok = dialogView.findViewById(R.id.ok);


                    final AlertDialog b = alertbox.create();
                    b.show();
                    materialmSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                        }
                    });
                    try {
                        result = new OrderHelper.GetOrderFinalItems().execute(holder.roomId.getText().toString().split("\\.")[0]).get();
                        if (result.isEmpty()) {
                        } else {
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<CustomerOrderModel>>() {
                            }.getType();
                            RoomModels = new Gson().fromJson(result, listType);
                            CustomerOrderItemsAdapter customerOrderItemsAdapter = new CustomerOrderItemsAdapter(mCtx, RoomModels);
                            MaterialrecyclerView.setAdapter(customerOrderItemsAdapter);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            b.dismiss();
                        }
                    });
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

                                result = new OrderHelper.PostOrderItemDelete().execute(holder.roomId.getText().toString().split("\\.")[0]).get();
                                try {
                                    if (result.isEmpty()) {
                                        Toast.makeText(mCtx, "Result empty", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Toast.makeText(mCtx, "Deleted", Toast.LENGTH_SHORT).show();


                                    }

                                } catch (Exception e) {
                                }

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

        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return RoomModels.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private Context mCtx;
        private ImageButton edit, deletebtn;

        private List<CustomerOrderModel> RoomModels;
        private RoomModel completeOrderModel;
        private long order_id = 0;
        private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
        final TextView room_name,orderId,roomId;

        public OrderItemViewHolder(View view, Context mCtx, List<CustomerOrderModel> RoomModels) {
            super(view);
            this.mCtx = mCtx;
            this.RoomModels = RoomModels;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            room_name = view.findViewById(R.id.roomTypes);
            orderId = view.findViewById(R.id.orderIDs);
            roomId = view.findViewById(R.id.roomIDs);
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