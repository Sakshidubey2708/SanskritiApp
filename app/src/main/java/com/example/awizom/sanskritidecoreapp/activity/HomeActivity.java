package com.example.awizom.sanskritidecoreapp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.adapter.OrderCustomerFollowUpAdapter;
import com.example.awizom.sanskritidecoreapp.fragment.BottomCustomerFragment;
import com.example.awizom.sanskritidecoreapp.fragment.BottomOrderFragment;
import com.example.awizom.sanskritidecoreapp.fragment.BottomStatusFragment;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.modelnew.OrderNewOne;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Fragment customerLayoutfragment, orderLayoutfragment, statusLayoutFragment;
    private Fragment fragment = null;
    boolean connected = false;
    private TextView naMe, contact;
    RecyclerView recyclerView;
    private String result = "";
    private List<OrderNewOne> orderList;
    OrderCustomerFollowUpAdapter adapter;
    private ImageView imageViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            checkInternet();
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            if (SharedPrefManager.getInstance(HomeActivity.this).getUser().userRole.equals("User")) {
                Menu menu = navigationView.getMenu();
                MenuItem target = menu.findItem(R.id.nav_userpermission);
                target.setVisible(false);
            } else {

            }

            View headerview = navigationView.getHeaderView(0);
            naMe = headerview.findViewById(R.id.name);
            contact = headerview.findViewById(R.id.customer_ontact);
            naMe.setText(SharedPrefManager.getInstance(this).getUser().userName);

            customerLayoutfragment = new BottomCustomerFragment();
            orderLayoutfragment = new BottomOrderFragment();
            statusLayoutFragment = new BottomStatusFragment();
            BottomNavigationView navigation = findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        ActivityCompat.requestPermissions(HomeActivity.this,
//                new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE},
//                1);


//        imageViews = findViewById(R.id.imgView);
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            try {
                getOrderList();
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.v("TAG", "Permission is granted");

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(HomeActivity.this);
            alertbox.setIcon(R.drawable.ic_warning_black_24dp);
            alertbox.setTitle("Do You Want To Exit Programme?");
            alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    // finish used for destroyed activity
                    finishAffinity();
                    System.exit(0);
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

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            SharedPrefManager.getInstance(this).logout();
            Intent login = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(login);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Measurement) {
            Intent intent = new Intent(this, MesurementListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_tailorlist) {
            Intent intent = new Intent(this, TailorActivityList.class);
            startActivity(intent);
        } else if (id == R.id.nav_roomlist) {
            Intent intent = new Intent(this, RoomList.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {

            Intent intent = new Intent(this, SearchListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_userpermission) {

            Intent intent = new Intent(this, UserListActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_karigar) {

            Intent intent = new Intent(this, KarigarAddActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_masterMaterial) {

            Intent intent = new Intent(this, MasterMaterialActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_subMasterMaterial) {

            Intent intent = new Intent(this, MasterSubMAterialActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_changepassword) {

            Intent intent = new Intent(this, ChangePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_About) {

            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

            new AlertDialog.Builder(HomeActivity.this)
                    .setTitle("Work Only !")
                    .setMessage("When Live on Play Store")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                        }
                    }).show();
//            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//            sharingIntent.setType("text/plain");
//            String shareBody = "Sanskriti Decoration";
//            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
//            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//            startActivity(Intent.createChooser(sharingIntent, "Share via"));
//            overridePendingTransition(R.anim.slide_out, R.anim.slide_in);

        } else if (id == R.id.nav_send) {
            new AlertDialog.Builder(HomeActivity.this)
                    .setTitle("Work Only !")
                    .setMessage("When Live on Play Store")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                        }
                    }).show();
//            String phoneNumber = "", message = "hello Sanskriti";
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
//            intent.putExtra("sms_body", message);
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
        } else if (id == R.id.nav_logout) {
            SharedPrefManager.getInstance(this).logout();
            Intent login = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(login);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Class fragmentClass = null;
            switch (item.getItemId()) {
                case R.id.navigation_customer:
                    if ((SharedPrefManager.getInstance(HomeActivity.this).getUser().userRole.contains("Admin"))) {
                        getSupportActionBar().setTitle("Customer Details");
                        fragment = customerLayoutfragment;
                        fragmentClass = BottomCustomerFragment.class;
                    } else if ((SharedPrefManager.getInstance(HomeActivity.this).getUser().userRole.contains("User"))) {
                        // userPermissionGet();

                    } else {
                        Toast toast = Toast.makeText(HomeActivity.this, "Not Permitted", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    break;
                case R.id.navigation_Order:
                    if ((SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserRole().contains("Admin")) ||
                            (SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserRole().contains("User"))) {
                        getSupportActionBar().setTitle("Order Details");
                        fragment = orderLayoutfragment;
                        fragmentClass = BottomOrderFragment.class;
                    } else {
                        Toast.makeText(getApplicationContext(), "User Is Not Permitted", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.navigation_status:
                    getSupportActionBar().setTitle("Status Details");
                    fragment = statusLayoutFragment;
                    fragmentClass = BottomStatusFragment.class;
                    break;

            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.home_container, fragment).commit();
                setTitle("");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    };

    private void checkInternet() {

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network

                connected = true;
                //    Toast.makeText(getApplicationContext(), "Internet is On", Toast.LENGTH_SHORT).show();
            } else {
                connected = false;
                imageViews.setVisibility(View.VISIBLE);
                Snackbar.make(getWindow().getDecorView().getRootView(), "No internet connection, retry", Snackbar.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getOrderList() {
        try {

            result = new OrderHelper.GetCustomerFollowUpList().execute(SharedPrefManager.getInstance(HomeActivity.this).getUser().access_token).get();

            if (result != null) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<OrderNewOne>>() {
                }.getType();
                orderList = new Gson().fromJson(result, listType);

                if (!orderList.equals(null)) {
                    Log.d("Error", orderList.toString());
                    adapter = new OrderCustomerFollowUpAdapter(this, orderList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Server Not Connected", Snackbar.LENGTH_LONG).show();

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
