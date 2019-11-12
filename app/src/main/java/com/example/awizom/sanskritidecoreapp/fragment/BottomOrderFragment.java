package com.example.awizom.sanskritidecoreapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.activity.AfterCreateActivity;
import com.example.awizom.sanskritidecoreapp.activity.CustomerfollowUpList;
import com.example.awizom.sanskritidecoreapp.activity.GetCustomerOrderListActivity;
import com.example.awizom.sanskritidecoreapp.activity.NewOrderListActivity;
import com.example.awizom.sanskritidecoreapp.activity.OrderCreate;
import com.example.awizom.sanskritidecoreapp.activity.OrderDecorActivity;
import com.example.awizom.sanskritidecoreapp.activity.SignInActivity;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.PermissionList;
import com.example.awizom.sanskritidecoreapp.model.UserModel;
import com.example.awizom.sanskritidecoreapp.model.UserPermissionModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class BottomOrderFragment extends Fragment implements View.OnClickListener {


    private TextView pendingOrderList, pendingOrderCreate,Orderlist,CreatesOrder;
    private LinearLayout OrderLiner,OrderlistLinear, pendingOrderLiner,CreatesOrderLinearLayoyt;
    private Intent intent;
    private Fragment pendinOrderListFragment, orderCreate;
    Fragment fragment = null;
    String[] values;
    String pendingForAdv = "0",permissionStatusName="";
  //  SwipeRefreshLayout mSwipeRefreshLayout;
    private UserPermissionModel userPermissionModel;
    private List<PermissionList> permissionList;
    List<UserModel> userItemList;
    String userId;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_order_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

       // statusCountGETmethodCall();
        progressDialog = new ProgressDialog(getContext());
        pendingOrderList = view.findViewById(R.id.pendingOrder);
        pendingOrderCreate = view.findViewById(R.id.orderCreate);
        Orderlist = view.findViewById(R.id.orderList);
        pendingOrderList.setOnClickListener(this);
        pendingOrderCreate.setOnClickListener(this);
        Orderlist.setOnClickListener(this);

        CreatesOrder = view.findViewById(R.id.CreatesOrder);
        CreatesOrderLinearLayoyt = view.findViewById(R.id.CreateOrdeLayout);
        CreatesOrder.setOnClickListener(this);
        CreatesOrderLinearLayoyt.setOnClickListener(this);



        OrderLiner = view.findViewById(R.id.orderLinearLayout);
        OrderlistLinear = view.findViewById(R.id.orderListLayout);
        pendingOrderLiner = view.findViewById(R.id.pendingLinear);
        OrderLiner.setOnClickListener(this);
        OrderlistLinear.setOnClickListener(this);
        pendingOrderLiner.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Class fragmentClass = null;
        switch (v.getId()) {

            case R.id.orderCreate:
                intent = new Intent(getContext(), OrderDecorActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                break;

            case R.id.orderLinearLayout:
                intent = new Intent(getContext(), OrderDecorActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                break;

            case R.id.orderListLayout:
                intent = new Intent(getContext(), GetCustomerOrderListActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_out, R.anim.slide_in);

                break;
            case R.id.orderList:
                intent = new Intent(getContext(), GetCustomerOrderListActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                break;

            case R.id.pendingLinear:
                intent = new Intent(getContext(), CustomerfollowUpList.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_out, R.anim.slide_in);

                break;
            case R.id.pendingOrder:
                intent = new Intent(getContext(), CustomerfollowUpList.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                break;
            case R.id.CreateOrdeLayout:
                intent = new Intent(getContext(), OrderCreate.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_out, R.anim.slide_in);

                break;
            case R.id.CreatesOrder:
                intent = new Intent(getContext(), OrderCreate.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                break;


        }

    }

    private void statusCountGETmethodCall() {
        try {
            new statusCountGET().execute(SharedPrefManager.getInstance(getContext()).getUser().access_token);
        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private class statusCountGET extends AsyncTask<String, Void, String> implements View.OnClickListener {
        @Override
        protected String doInBackground(String... params) {

            String json = "";
            String accesstoken = params[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "StatusCountGet");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
                SharedPrefManager.getInstance(getContext()).logout();
                Intent login = new Intent(getContext(), SignInActivity.class);
                startActivity(login);
            }
            return json;
        }

        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<String[]>() {
                    }.getType();
                    values = gson.fromJson(result, listType);
                    pendingForAdv = "Pending For Advance " + " (" + values[0].split("=")[1] + ")";
                   // pendingOrderList.setText(pendingForAdv);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {
        }
    }

    private void userPermissionGet() {
        try {
            new userPermissionGetDetail().execute(SharedPrefManager.getInstance(getContext()).getUser().userID,SharedPrefManager.getInstance(getContext()).getUser().access_token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class userPermissionGetDetail extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String userID = params[0];
            String accesstoken = params[1];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "UserPermissionGet/" + userID);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            }
            return json;
        }
        protected void onPostExecute(String result) {
            if (result.isEmpty()) {

                // Toast.makeText(getContext(), "Invalid request results is empty", Toast.LENGTH_SHORT).show();
            }else {
                Gson gson = new Gson();
                Type listType = new TypeToken<UserPermissionModel>() {
                }.getType();
                if(SharedPrefManager.getInstance(getContext()).getUser().getUserRole().equals("Admin")) {
                    return;
                }
                userPermissionModel = new Gson().fromJson(result, listType);
                if(userPermissionModel != null){

                    permissionList = userPermissionModel.getPermissionList();
                    List<String> perList = new ArrayList<>();
                    for(PermissionList up : permissionList){
                        perList.add(up.getPermissionName());
                    }
                    if(perList.contains( permissionStatusName ))
                    {
                        if (permissionStatusName.equals("OrderCreate")) {
                            intent = new Intent(getContext(), AfterCreateActivity.class);
                            intent = intent.putExtra("FilterKey", "orderCreate");
                            intent = intent.putExtra("StatusName", "CreateOrder ");
                            startActivity(intent);


                        } else if (permissionStatusName.equals("Advance")) {
                            intent = new Intent(getContext(), NewOrderListActivity.class);
                            intent = intent.putExtra("FilterKey", "pandingForAdv");
                            intent = intent.putExtra("ButtonName", "Cancel Order");
                            intent = intent.putExtra("StatusName", "Cancel");
                            intent = intent.putExtra("DailogMessage", "Do you want to change the status");
                            startActivity(intent);
                        }
                    }
                    else {

                        Toast toast = Toast.makeText(getContext(), "Not Permitted", Toast.LENGTH_SHORT);
                        toast.show();

                    }



                }

            }
        }
    }

}
