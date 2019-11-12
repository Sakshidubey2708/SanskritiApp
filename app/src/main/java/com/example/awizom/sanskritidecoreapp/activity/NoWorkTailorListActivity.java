package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.Document;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NoWorkTailorListActivity extends AppCompatActivity {

    ListView lv;
    ImageButton addtelor;
    // List <TelorModel> list1;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Handler handler = new Handler();
    Runnable refresh;
    private Button add, cancel;
    private EditText t_name, old_t_name;
    ArrayAdapter<String> telorListAapter;
    String[] telorlist;
    private String telornamet, telorname_old;
    Document doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.telor_list_item);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tailor List");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in, R.anim.slide_out);
                onBackPressed();
            }
        });

        toolbar.setSubtitleTextAppearance(getApplicationContext(), R.style.styleA);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.styleA);
        toolbar.setTitleTextColor(Color.WHITE);
        initView();
    }


    private void initView() {
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        //    progressDialog = new ProgressDialog(getActivity());
        //  progressDialog.setMessage("Please wait while loading telors");
        lv = findViewById(R.id.telorList);
        addtelor = findViewById(R.id.addtelor);
        //img2=view.findViewById(R.id.updateButton1);
        //   img3=view.findViewById(R.id.updateButton2);
        //     lv = view.findViewById(R.id.telorList);
        doc = new Document();


        addtelor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.telor_dialog, null);
                alertbox.setView(dialogView);

                t_name = dialogView.findViewById(R.id.sNo);
                old_t_name = dialogView.findViewById(R.id.telornumber);
                add = dialogView.findViewById(R.id.add);
                cancel = dialogView.findViewById(R.id.cancelButton);
                alertbox.setTitle("Add telor");
                final AlertDialog b = alertbox.create();
                b.show();

                //   telorname=t_name.getText().toString();


                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (t_name.getText().toString().isEmpty() || old_t_name.getText().toString().trim()
                                .length() < 10) {
                            t_name.setError("Required !");
                            old_t_name.setError("Required !");
                        } else {
                            postTelorList();
                            t_name.setText("");
                            old_t_name.setText("");
                        }

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.dismiss();


                    }
                });

            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getTelorList();
            }
        });
        getTelorList();
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                final AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
//                LayoutInflater inflater = getLayoutInflater();
//                final View dialogView = inflater.inflate(R.layout.telor_dialog_edittelor, null);
//                alertbox.setView(dialogView);
//
//                t_name = dialogView.findViewById(R.id.sNo);
//                old_t_name = dialogView.findViewById(R.id.oldname);
//
//
//                add = dialogView.findViewById(R.id.add);
//                cancel = dialogView.findViewById(R.id.cancelButton);
//                alertbox.setTitle("Modify telor");
//                final AlertDialog b = alertbox.create();
//                b.show();
//
//
//                t_name.setText(telorlist[position]);
//                old_t_name.setHint(telorlist[position]);
//                old_t_name.setVisibility(View.GONE);
//                old_t_name.setText(telorlist[position]);
//
//
//                add.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        postTelorListEdit();
//                    }
//                });
//
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        b.dismiss();
//
//                    }
//                });
//
//
//            }
//        });
    }


    private void postTelorListEdit() {
        telornamet = t_name.getText().toString();
        telorname_old = old_t_name.getText().toString();

        try {

            new PostTelorDetailsEdit().execute(telornamet.trim(), telorname_old.trim(), SharedPrefManager.getInstance(NoWorkTailorListActivity.this).getUser().access_token);


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(NoWorkTailorListActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    class PostTelorDetailsEdit extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            String telorname = params[0];
            String telorname_old = params[1];
            String accesstoken = params[2];

            String json = "";
            try {

                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "TelorPost/" + telorname + "/" + telorname_old);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                builder.post(parameters.build());

                okhttp3.Response response = client.newCall(builder.build()).execute();

                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(NoWorkTailorListActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }

            return json;
        }

        protected void onPostExecute(String result) {

            if (result.isEmpty()) {
                //progressDialog.dismiss();
                //  Toast.makeText(getContext(), "Invalid request", Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                final Result jsonbodyres = gson.fromJson(result, Result.class);
                Toast.makeText(NoWorkTailorListActivity.this
                        , jsonbodyres.getMessage(), Toast.LENGTH_SHORT).show();
                if (jsonbodyres.getStatus() == true) {
                    // modifyItem(pos,um);
                }
            }
        }
    }


    private void postTelorList() {
        telornamet = t_name.getText().toString();

        try {
            //progressDialog.setMessage("loading...");
            //progressDialog.show();
            new PostTelorDetails().execute(telornamet.trim(), SharedPrefManager.getInstance(NoWorkTailorListActivity.this).getUser().access_token);


        } catch (Exception e) {
            e.printStackTrace();
            //progressDialog.dismiss();
            Toast.makeText(NoWorkTailorListActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    class PostTelorDetails extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            String telorname = params[0];
            String accesstoken = params[1];

            String json = "";
            try {

                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "TelorPost/" + telorname + "/" + "blank");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                builder.post(parameters.build());

                okhttp3.Response response = client.newCall(builder.build()).execute();

                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //  progressDialog.dismiss();
                Toast.makeText(NoWorkTailorListActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }

            return json;
        }

        protected void onPostExecute(String result) {

            if (result.isEmpty()) {
                //progressDialog.dismiss();
                //    Toast.makeText(getContext(), "Invalid request", Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                final Result jsonbodyres = gson.fromJson(result, Result.class);
                Toast.makeText(NoWorkTailorListActivity.this
                        , jsonbodyres.getMessage(), Toast.LENGTH_SHORT).show();
                if (jsonbodyres.getStatus() == true) {
                    // modifyItem(pos,um);

                    //  progressDialog.dismiss();
                }

                //progressDialog.dismiss();
            }


        }


    }


    private void getTelorList() {
        try {
            mSwipeRefreshLayout.setRefreshing(true);
            new GetTelorDetails().execute(SharedPrefManager.getInstance(NoWorkTailorListActivity.this).getUser().access_token);


        } catch (Exception e) {
            e.printStackTrace();
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(NoWorkTailorListActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }


    private class GetTelorDetails extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String accesstoken = params[0];
            String json = "";
            try {

                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "TelorListGet");
                builder.addHeader("Content-Type", "application/json");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Content-Length", "0");
                builder.addHeader("Authorization", "Bearer " + accesstoken);

                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(NoWorkTailorListActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }

            return json;
        }

        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    //      Toast.makeText(getActivity(), "Invalid request", Toast.LENGTH_SHORT).show();
                } else {

                    Gson gson = new Gson();
                    Type listType = new TypeToken<String[]>() {
                    }.getType();
                    telorlist = new Gson().fromJson(result, listType);
                    telorListAapter = new ArrayAdapter<String>(NoWorkTailorListActivity.this, R.layout.layout_button_telorlist, R.id.label, telorlist);
                    lv.setAdapter(telorListAapter);
                    mSwipeRefreshLayout.setRefreshing(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
