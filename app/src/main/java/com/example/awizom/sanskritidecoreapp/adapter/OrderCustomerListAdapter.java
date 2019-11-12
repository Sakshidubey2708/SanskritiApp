package com.example.awizom.sanskritidecoreapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.modelnew.CompleteOrderModel;
import com.example.awizom.sanskritidecoreapp.modelnew.OrderNewOne;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class OrderCustomerListAdapter extends RecyclerView.Adapter<OrderCustomerListAdapter.OrderItemViewHolder> {

    private Context mCtx;
    private List<OrderNewOne> orderitemList;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public OrderCustomerListAdapter(Context mCtx, List<OrderNewOne> orderitemList) {
        this.mCtx = mCtx;
        this.orderitemList = orderitemList;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.allcustomerlist, null);
        return new OrderItemViewHolder(view, mCtx, orderitemList);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderItemViewHolder holder, int position) {
        OrderNewOne orderNewOne = orderitemList.get(position);
        try {
            holder.orderid.setText(String.valueOf(orderNewOne.getOrderId()));
            holder.customerNames.setText(orderNewOne.getCustomerName());
            holder.customerMobile.setText(orderNewOne.getMobile());
            holder.date.setText(orderNewOne.getOrderDate().toString().split("T")[0]);
            holder.roomType.setText(orderNewOne.getRoom_type());
            holder.materialType.setText(orderNewOne.getSubMaterialType());

            holder.submeasurType.setText(orderNewOne.getSubUMOType());
            holder.design.setText(orderNewOne.getDesign());
            holder.quantity.setText(String.valueOf(orderNewOne.getQuantity()));

            holder.mrp.setText(String.valueOf(orderNewOne.getMrp()));
            holder.discount.setText(String.valueOf(orderNewOne.getDiscount()));
            holder.totalAmount.setText(String.valueOf(orderNewOne.getAfterDiscountAmt()));

            holder.GetCustomerCompleteOrder(holder.orderid.getText().toString());
            final String order_ids = holder.orderid.getText().toString();

            holder.pdfBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.BASE_URL+"Report/OrderDetail.aspx?OrderID="+order_ids));
                        mCtx.startActivity(browserIntent);

                    } catch (Exception E) {
                        E.printStackTrace();
                    }
                }
            });


            holder.vieew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(buttonClick);
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                    LayoutInflater inflater = LayoutInflater.from(mCtx);
                    final View dialogView = inflater.inflate(R.layout.view_order_detail, null);
                    alertbox.setView(dialogView);
                    alertbox.setCancelable(false);
                    final TextView customner_name,room_name,material_name,quantity,mrpamt,afeterdiscountamt,adavanceamt,pendingamt,discountamt;
                    final Button ok;
                    final ImageButton delete;


                    customner_name = dialogView.findViewById(R.id.custname);
                    room_name = dialogView.findViewById(R.id.roomTypes);
                    material_name = dialogView.findViewById(R.id.materialtype);
                    quantity = dialogView.findViewById(R.id.quantities);
                    mrpamt = dialogView.findViewById(R.id.mrpAmount);
                    adavanceamt = dialogView.findViewById(R.id.AdvanceAmount);
                    pendingamt = dialogView.findViewById(R.id.PendingAmount);
                    discountamt = dialogView.findViewById(R.id.DiscountAmount);
                    afeterdiscountamt = dialogView.findViewById(R.id.afterDiscountAmount);


                    ok = dialogView.findViewById(R.id.okButton);
                    delete = dialogView.findViewById(R.id.deltbtn);
                    alertbox.setTitle("View Order Details");

                    final AlertDialog b = alertbox.create();
                    b.show();


                    customner_name.setText(holder.customerNames.getText().toString());
                    room_name.setText(holder.roomType.getText().toString());
                    material_name.setText(holder.materialType.getText().toString());
                    quantity.setText(holder.quantity.getText().toString());
                    mrpamt.setText(holder.mrp.getText().toString());
                    discountamt.setText(holder.discount.getText().toString());
                    afeterdiscountamt.setText(holder.totalAmount.getText().toString());

                    discountamt.setText(holder.discount.getText().toString());




                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
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
        return orderitemList.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private Context mCtx;
        private TextView customerNames, customerMobile, materialType, date,orderid,roomType,
                submeasurType,design,quantity,mrp,discount,totalAmount,vieew;
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

            orderid = view.findViewById(R.id.orderID);
            customerNames = view.findViewById(R.id.customerName);
            customerMobile = view.findViewById(R.id.customer_ontact);
            date = view.findViewById(R.id.orderdate);
            roomType = view.findViewById(R.id.RoomName);
            materialType = view.findViewById(R.id.materialType);
            submeasurType = view.findViewById(R.id.measure);

            design = view.findViewById(R.id.design);
            quantity = view.findViewById(R.id.quantity);
            mrp = view.findViewById(R.id.mrp);
            discount = view.findViewById(R.id.discout);
            totalAmount = view.findViewById(R.id.afterDiscont);

            vieew = view.findViewById(R.id.views);
            pdfBtn = view.findViewById(R.id.pdfButton);

        }

        private void GetCustomerCompleteOrder(String oId) {
            try {
                String result="";

                result = new OrderHelper.GetCompleteOrderModel().execute(oId,SharedPrefManager.getInstance(mCtx).getUser().access_token).get();
                if (result.isEmpty()) {

                } else {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<CompleteOrderModel>() {
                    }.getType();
                    completeOrderModel = new Gson().fromJson(result, listType);
                    if (completeOrderModel != null) {
                        order_id = completeOrderModel.getOrderid();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                //  Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
                ///
            }
        }

        @Override
        public void onClick(final View v) {
            v.startAnimation(buttonClick);
            if(pdfBtn.getId() == v.getId()){
//                try {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://Decore.somee.com/Report/OrderDetail.aspx?OrderID="+order_id));
//                    mCtx.startActivity(browserIntent);
//
//                } catch (Exception E) {
//                    E.printStackTrace();
//                }
//                Toast.makeText(mCtx, "lc: ", Toast.LENGTH_SHORT).show();
            }

        }


        @Override
        public boolean onLongClick(View v) {

            return true;
        }




    }
}
