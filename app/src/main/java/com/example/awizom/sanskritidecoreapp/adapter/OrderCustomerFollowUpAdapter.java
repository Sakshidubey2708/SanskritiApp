package com.example.awizom.sanskritidecoreapp.adapter;

import android.Manifest;
import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.modelnew.CompleteOrderModel;
import com.example.awizom.sanskritidecoreapp.modelnew.OrderNewOne;

import java.util.List;

public class OrderCustomerFollowUpAdapter extends RecyclerView.Adapter<OrderCustomerFollowUpAdapter.OrderItemViewHolder> {

    private Context mCtx;
    private List<OrderNewOne> orderitemList;
    Button callbtn;
    TextView calltext;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    public OrderCustomerFollowUpAdapter(Context mCtx, List<OrderNewOne> orderitemList) {
        this.mCtx = mCtx;
        this.orderitemList = orderitemList;
    }

    @NonNull
    @Override
    public OrderCustomerFollowUpAdapter.OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cus_follow_adapter_layout, null);
        return new OrderCustomerFollowUpAdapter.OrderItemViewHolder(view, mCtx, orderitemList);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderCustomerFollowUpAdapter.OrderItemViewHolder holder, int position) {
        OrderNewOne orderNewOne = orderitemList.get(position);
        try {

            holder.customerNames.setText(orderNewOne.getCustomerName());
            holder.customerMobile.setText(orderNewOne.getMobile());
            if(orderNewOne.getInteriorName() == null){
                holder.interiorName.setText("----");
            }else {
                holder.interiorName.setText(orderNewOne.getInteriorName());
            }

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    v.startAnimation(buttonClick);
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                    LayoutInflater inflater = LayoutInflater.from(mCtx);
                    final View dialogView = inflater.inflate(R.layout.calldialog, null);
                    alertbox.setView(dialogView);
                    calltext = dialogView.findViewById(R.id.textcall);

                    callbtn = dialogView.findViewById(R.id.callbtn);
                    alertbox.setTitle("Call Customer");
                    calltext.setText(holder.customerMobile.getText().toString());

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
                    return false;
                }
            });




        } catch (Exception E) {
            E.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return orderitemList.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private Context mCtx;
        private TextView customerNames, customerMobile, interiorName;
        private ImageButton pdfBtn;
        private List<OrderNewOne> orderitemList;
        private CompleteOrderModel completeOrderModel;
        private  long order_id=0;
        private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        public OrderItemViewHolder(View view, Context mCtx, List<OrderNewOne> orderitemList) {
            super(view);
            this.mCtx = mCtx;
            this.orderitemList = orderitemList;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            customerNames = view.findViewById(R.id.customerName);
            customerMobile = view.findViewById(R.id.customer_ontact);
            interiorName = view.findViewById(R.id.RoomName);

        }

        @Override
        public void onClick(final View v) {
            v.startAnimation(buttonClick);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }


}

