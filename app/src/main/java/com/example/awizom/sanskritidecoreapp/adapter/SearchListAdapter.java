package com.example.awizom.sanskritidecoreapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.DataOrder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchItemViewHolder> {
    private Context mCtx;
    private List<DataOrder> searchDataOrders;
    private String statusName;
    private DataOrder searchViewData;
    private String statusMessage;
    AlertDialog.Builder alertbox,dialogBuilder;
    String valueResult;

    AlertDialog b;
   private View dialogView;
   private  Spinner spinner;

    public SearchListAdapter(Context mCtx, List<DataOrder> searchItemsDataOrders, String statusName) {
        this.mCtx = mCtx;
        this.statusName = statusName;
        this.searchDataOrders = searchItemsDataOrders;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.search_list, null);
        return new SearchItemViewHolder(view, mCtx, searchDataOrders);
    }


    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        searchViewData = searchDataOrders.get(position);
        try {
            holder.c_name.setText("Name\n" + searchViewData.getCustomerName().trim());
            holder.c_contact.setText("Address\n " + searchViewData.getMobile().trim());
            holder.c_address.setText("Mobile\n " + searchViewData.getAddress().trim());
            holder.c_oredrDate.setText("Date\n " + searchViewData.getOrderDate().split("T")[0].trim());
            holder.c_advance.setText("Advance\n " + Double.toString(searchViewData.getAdvance()).trim());
            holder.c_totalAmount.setText("Amount\n " + Double.toString(searchViewData.getTotalAmount()).trim());


        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return searchDataOrders.size();
    }

    class SearchItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context mCtx;
        private List<DataOrder> searchDataOrdersList;
        private TextView c_name, c_contact, c_address, c_oredrDate, c_advance, c_totalAmount;


        public SearchItemViewHolder(View itemView, Context mCtx, List<DataOrder> searchItemsDataOrders) {
            super(itemView);
            this.mCtx = mCtx;
            this.searchDataOrdersList = searchItemsDataOrders;
            itemView.setOnClickListener(this);
            c_name = itemView.findViewById(R.id.textViewCustomerName);
            c_address = itemView.findViewById(R.id.textViewCustomerAddress);
            c_contact = itemView.findViewById(R.id.textViewMobile);
            c_oredrDate = itemView.findViewById(R.id.textViewOrderDate);
            c_advance = itemView.findViewById(R.id.textViewAdvance);
            c_totalAmount = itemView.findViewById(R.id.textViewATotalAmount);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            DataOrder orderitem = this.searchDataOrdersList.get(position);
        }

    }



}