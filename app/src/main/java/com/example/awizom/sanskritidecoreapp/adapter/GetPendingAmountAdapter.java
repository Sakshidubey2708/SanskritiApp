package com.example.awizom.sanskritidecoreapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.modelnew.OrderNewOne;
import com.google.gson.Gson;

import java.util.List;

public class GetPendingAmountAdapter extends RecyclerView.Adapter<GetPendingAmountAdapter.OrderItemViewHolder> {

    private Context mCtx;
    private List<OrderNewOne> OrderNewOnes;
    TextView calltext;
    private String result="";
    private   boolean isAmount=true;

    public GetPendingAmountAdapter(Context mCtx, List<OrderNewOne> OrderNewOnes) {
        this.mCtx = mCtx;
        this.OrderNewOnes = OrderNewOnes;
    }

    @NonNull
    @Override
    public GetPendingAmountAdapter.OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.panding_amount_list, null);
        return new GetPendingAmountAdapter.OrderItemViewHolder(view, mCtx, OrderNewOnes);
    }

    @Override
    public void onBindViewHolder(@NonNull final GetPendingAmountAdapter.OrderItemViewHolder holder, int position) {
        OrderNewOne OrderNewOne = OrderNewOnes.get(position);
        try {

            holder.statuss.setText(String.valueOf(OrderNewOne.getAmount_total()));
            holder.orders.setText(String.valueOf(OrderNewOne.getOrder_type()));
            holder.custNames.setText(OrderNewOne.getCustomerName());
            holder.amountPnding.setText(String.valueOf(OrderNewOne.getAmount_pending()));
            holder.amtTotal.setText(String.valueOf(OrderNewOne.getAmount_total()));
            holder.orderid.setText(String.valueOf(OrderNewOne.getOrderid()));

            if(!OrderNewOne.isIs_amountstatus() == false){
                holder.statuss.setText("Received");
                holder.statuss.setTextColor(mCtx.getResources().getColor(R.color.green));
                holder.checkeds.setVisibility(View.VISIBLE);
                holder.receive.setVisibility(View.GONE);

            }else {
                holder.statuss.setText("Pending");
                holder.statuss.setTextColor(mCtx.getResources().getColor(R.color.red));
                holder.receive.setVisibility(View.VISIBLE);
            }


            holder.receive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean check= true;
                    android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(mCtx);
                    alertbox.setIcon(R.drawable.ic_warning_black_24dp);
                    alertbox.setTitle("Do You Want To Change Status ?");
                    alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                isAmount=true;
                                result=   new OrderHelper.UpdatePostPendingAmount().execute( SharedPrefManager.getInstance(mCtx).getUser().access_token,
                                        holder.orderid.getText().toString(),String.valueOf(isAmount)).get();

                                try {
                                    if (result.isEmpty()) {
                                        Toast.makeText(mCtx, "empty", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Gson gson = new Gson();
                                        if (result.equals(false)) {
//                                            holder.receive.setText("Complete");
                                            holder.statuss.setText("Received");
                                            holder.statuss.setTextColor(mCtx.getResources().getColor(R.color.green));
                                            holder.checkeds.setVisibility(View.VISIBLE);
                                            holder.receive.setVisibility(View.GONE);
                                        }
                                        Toast.makeText(mCtx, "Success", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(mCtx, "exception", Toast.LENGTH_SHORT).show();
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
        return OrderNewOnes.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private Context mCtx;
        private TextView statuss,orders,custNames,amountPnding,amtTotal,orderid;
        private Button receive;
        private ImageView checkeds;
        private List<OrderNewOne> OrderNewOnes;
        private OrderNewOne completeOrderModel;
        private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        public OrderItemViewHolder(View view, Context mCtx, List<OrderNewOne> OrderNewOnes) {
            super(view);
            this.mCtx = mCtx;
            this.OrderNewOnes = OrderNewOnes;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            statuss = view.findViewById(R.id.status);
            orders = view.findViewById(R.id.order);
            custNames = view.findViewById(R.id.custName);
            amountPnding = view.findViewById(R.id.amountPendimg);
            amtTotal = view.findViewById(R.id.orderdate);
            receive = view.findViewById(R.id.receive);
            orderid = view.findViewById(R.id.orderid);
            checkeds = view.findViewById(R.id.checked);
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