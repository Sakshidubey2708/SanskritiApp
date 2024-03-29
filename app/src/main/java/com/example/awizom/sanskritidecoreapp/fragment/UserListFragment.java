package com.example.awizom.sanskritidecoreapp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.adapter.UserListAdapter;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class UserListFragment extends Fragment {

    //  ProgressDialog progressDialog;
    List<UserModel> userItemList;
    UserListAdapter adapter;
    RecyclerView recyclerView;
    String userId;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Handler handler = new Handler();
    Runnable refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_list_item, container, false);
        initView(view);
        return view;

    }

    private void initView(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Please wait while loading users");
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getUserList();
            }
        });
        getUserList();
    }

    private void getUserList() {
        try {
//            progressDialog.setMessage("loading...");
//            progressDialog.show();
            mSwipeRefreshLayout.setRefreshing(true);
            new GetUserDetails().execute(SharedPrefManager.getInstance(getContext()).getUser().access_token);


        } catch (Exception e) {
            e.printStackTrace();
//            progressDialog.dismiss();
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }
    private class GetUserDetails extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String accesstoken = params[0];
            String json = "";
            try {

                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "AllUserGet");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //              progressDialog.dismiss();

            }

            return json;
        }

        protected void onPostExecute(String result) {

            if (result.isEmpty()) {
                //            progressDialog.dismiss();
           //     Toast.makeText(getActivity(), "Invalid request", Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<UserModel>>() {
                }.getType();
                userItemList = new Gson().fromJson(result, listType);

                for (UserModel cm : userItemList) {
                    userId = cm.getUserId();
                }

                adapter = new UserListAdapter(getContext(), userItemList);
                recyclerView.setAdapter(adapter);
                //          progressDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }


        }


    }


}
