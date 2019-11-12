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

public class GetPendingTailorAdapter extends RecyclerView.Adapter<GetPendingTailorAdapter.OrderItemViewHolder> {

    private Context mCtx;
    private List<OrderNewOne> OrderNewOnes;
    TextView calltext;
    private String result="";
    private   boolean isTailor=true;

    public GetPendingTailorAdapter(Context mCtx, List<OrderNewOne> OrderNewOnes) {
        this.mCtx = mCtx;
        this.OrderNewOnes = OrderNewOnes;
    }

    @NonNull
    @Override
    public GetPendingTailorAdapter.OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.panding_order_tailor_list, null);
        return new GetPendingTailorAdapter.OrderItemViewHolder(view, mCtx, OrderNewOnes);
    }

    @Override
    public void onBindViewHolder(@NonNull final GetPendingTailorAdapter.OrderItemViewHolder holder, int position) {
        OrderNewOne OrderNewOne = OrderNewOnes.get(position);
        try {

            holder.orders.setText(OrderNewOne.getOrder_type().toString());
            holder.tailorNames.setText(OrderNewOne.getT_name().toString());
            holder.orderid.setText(String.valueOf(OrderNewOne.getOrderid()));

            if(!OrderNewOne.isIs_tailorStatus() == false){
                holder.statuss.setText("Received");
                holder.statuss.setTextColor(mCtx.getResources().getColor(R.color.green));
                holder.checkd.setVisibility(View.VISIBLE);
                holder.receives.setVisibility(View.GONE);

            }else {
                holder.statuss.setText("Pending");
                holder.statuss.setTextColor(mCtx.getResources().getColor(R.color.red));
                holder.receives.setVisibility(View.VISIBLE);
                holder.checkd.setVisibility(View.GONE);
            }



            holder.receives.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean check= true;
                    android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(mCtx);
                    alertbox.setIcon(R.drawable.ic_warning_black_24dp);
                    alertbox.setTitle("Do You Want To Change Status ?");
                    alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                isTailor=true;
                                result=   new OrderHelper.UpdatePostPendingTailor().execute(SharedPrefManager.getInstance(mCtx).getUser().access_token,
                                        holder.orderid.getText().toString() , String.valueOf(isTailor)).get();

                                try {
                                    if (result.isEmpty()) {
                                        Toast.makeText(mCtx, "empty", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Gson gson = new Gson();
                                        if (result.equals(false)) {
                                            holder.statuss.setText("Received");
                                            holder.statuss.setTextColor(mCtx.getResources().getColor(R.color.green));
                                            holder.checkd.setVisibility(View.VISIBLE);
                                            holder.receives.setVisibility(View.GONE);
//                                            holder.receives.setText("Complete");
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
        private TextView tailorNames,orders,statuss,orderid;
        private Button receives;
        private ImageView checkd;
        private List<OrderNewOne> OrderNewOnes;
        private OrderNewOne completeOrderModel;
        private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        public OrderItemViewHolder(View view, Context mCtx, List<OrderNewOne> OrderNewOnes) {
            super(view);
            this.mCtx = mCtx;
            this.OrderNewOnes = OrderNewOnes;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tailorNames = view.findViewById(R.id.tailorName);
            orders = view.findViewById(R.id.order);
            statuss = view.findViewById(R.id.status);
            receives = view.findViewById(R.id.receive);
            orderid = view.findViewById(R.id.orderid);
            checkd = view.findViewById(R.id.checked);
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
