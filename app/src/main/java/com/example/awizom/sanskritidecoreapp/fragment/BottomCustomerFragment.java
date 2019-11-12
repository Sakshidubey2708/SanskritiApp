package com.example.awizom.sanskritidecoreapp.fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.activity.AddCustomerActivity;
import com.example.awizom.sanskritidecoreapp.activity.CustomerListActivity;
import com.example.awizom.sanskritidecoreapp.activity.CustomerModifyActivity;

public class BottomCustomerFragment extends Fragment implements View.OnClickListener {

    private TextView customerList, customerModify, customerAdd;
    private Intent intent;
    private LinearLayout LayoutcustomerList, LayoutcustomerModify, LayoutcustomerAdd;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_customer_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        progressDialog = new ProgressDialog(getContext());
        customerList = view.findViewById(R.id.customerList);
        customerModify = view.findViewById(R.id.modifyCustomer);
        customerAdd = view.findViewById(R.id.addCustomer);

        LayoutcustomerAdd = view.findViewById(R.id.AddcustomerLinear);
        LayoutcustomerModify = view.findViewById(R.id.modifylinearCustomer);
        LayoutcustomerList = view.findViewById(R.id.customerListLinear);

        customerList.setOnClickListener(this);
        customerModify.setOnClickListener(this);
        customerAdd.setOnClickListener(this);

        LayoutcustomerAdd.setOnClickListener(this);
        LayoutcustomerModify.setOnClickListener(this);
        LayoutcustomerList.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.addCustomer:
                progressDialog.setMessage("loading...");
                progressDialog.show();
                startActivity(intent = new Intent(getContext
                        (), AddCustomerActivity.class));
                progressDialog.dismiss();
                break;
            case R.id.modifyCustomer:
                startActivity(intent = new Intent(getContext(), CustomerModifyActivity.class));
                break;

            case R.id.customerList:
                startActivity(intent = new Intent(getContext(), CustomerListActivity.class));
                break;
            case R.id.AddcustomerLinear:
                startActivity(intent = new Intent(getContext(), AddCustomerActivity.class));
                break;
            case R.id.modifylinearCustomer:
                startActivity(intent = new Intent(getContext(), CustomerModifyActivity.class));
                break;

            case R.id.customerListLinear:
                startActivity(intent = new Intent(getContext(), CustomerListActivity.class));
                break;
        }


    }

}
