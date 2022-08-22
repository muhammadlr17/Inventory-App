package com.example.aplikasicrudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasicrudsqlite.db.DBSource;
import com.example.aplikasicrudsqlite.models.Inventory;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSubmit, btnBack;

    EditText edName, edPrice, edBrand;

    DBSource dbSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        edName = findViewById(R.id.ed_name);
        edPrice = findViewById(R.id.ed_price);
        edBrand = findViewById(R.id.ed_brand);

        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(this);

        dbSource = new DBSource(this);
        dbSource.open();

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateActivity.this.finish();
                Intent i = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View view) {
        String name = null;
        String brand = null;
        String price = null;

        Inventory inventory = null;

        if(TextUtils.isEmpty(edName.getText().toString().trim())
                && TextUtils.isEmpty((edPrice.getText().toString().trim()))
                && TextUtils.isEmpty(edBrand.getText().toString().trim())
        ){

            edName.setError("Please fill in the field");
            edPrice.setError("Please fill in the field");
            edBrand.setError("Please fill in the field");

        }else if(TextUtils.isEmpty((edName.getText().toString().trim())) ){
            edName.setError("Name field is required");

        }else if(TextUtils.isEmpty((edPrice.getText().toString().trim())) ){
            edPrice.setError("Price field is required");

        }else if(TextUtils.isEmpty((edBrand.getText().toString().trim())) ){
            edBrand.setError("Brand field is required");

        }else{
            name = edName.getText().toString().trim();
            price = edPrice.getText().toString().trim();
            brand = edBrand.getText().toString().trim();

            switch (view.getId()){
                case R.id.btn_submit:
                    inventory = dbSource.createInventory(name, brand, price);
                    Toast.makeText(this,"Added successfully",Toast.LENGTH_LONG).show();
                    CreateActivity.this.finish();
                    Intent i = new Intent(CreateActivity.this, ViewActivity.class);
                    startActivity(i);
                    break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbSource.close();
    }
}