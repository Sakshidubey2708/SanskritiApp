package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.awizom.sanskritidecoreapp.R;


public class AddMaterialActivity extends AppCompatActivity {
    EditText material_type, material_curtain, material_sofa, material_wallpaper, material_padding;
    Button addmaterial;
    String materialtype, materialcurtain, materialsofa, materialwallpaper, materialpadding;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Material");
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

        addmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                if (vatidation()) {
                    Toast.makeText(AddMaterialActivity.this, "error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddMaterialActivity.this, "successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        material_type = findViewById(R.id.materialtype);
        material_curtain = findViewById(R.id.materialcurtain);
        material_sofa = findViewById(R.id.materialsofa);
        material_wallpaper = findViewById(R.id.materialwallpaper);
        material_padding = findViewById(R.id.materialpadding);
        addmaterial = findViewById(R.id.materialbnt);

        materialtype = material_type.getText().toString();
        materialcurtain = material_curtain.getText().toString();
        materialsofa = material_sofa.getText().toString();
        materialwallpaper = material_wallpaper.getText().toString();
        materialpadding = material_padding.getText().toString();
    }

    private void AddMaterial() {


//        try {
//            progressDialog.setMessage("loading...");
//            progressDialog.show();
//            result = new Helper.AddCustomer().execute(SharedPrefManager.getInstance(this).getUser().userID,name, contact, address, interior, interiorContact).get();
//            try {
//                if (result.isEmpty()) {
//                    progressDialog.dismiss();
//                    Toast.makeText(AddCustomerActivity.this, "Result empty", Toast.LENGTH_SHORT).show();
//                } else {
//                    progressDialog.dismiss();
//                    Gson gson = new Gson();
//
//                }
//
//            } catch (Exception e) {
//                progressDialog.dismiss();
//                Toast.makeText(AddCustomerActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            progressDialog.dismiss();
//            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
//        }
    }


    private boolean vatidation() {
        init();
        if (material_type.getText().toString().isEmpty()) {
            material_type.setError("Required !");
        } else if (material_curtain.getText().toString().isEmpty()) {
            material_curtain.setError("Required !");
        } else if (material_sofa.getText().toString().isEmpty()) {
            material_sofa.setError("Required !");
        } else if (material_wallpaper.getText().toString().isEmpty()) {
            material_wallpaper.setError("Required !");
        } else if (material_padding.getText().toString().isEmpty()) {
            material_padding.setError("Required !");
        }
        return true;
    }

}