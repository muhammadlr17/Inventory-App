package com.example.aplikasicrudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aplikasicrudsqlite.db.DBSource;
import com.example.aplikasicrudsqlite.models.Inventory;

public class EditActivity extends AppCompatActivity {
    EditText edName, edPrice, edBrand;
    Button btnSubmit, btnCancel;

    DBSource dbSource;

    Inventory inventory;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edPrice = findViewById(R.id.ed_price);
        edName = findViewById(R.id.ed_name);
        edBrand = findViewById(R.id.ed_brand);

        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);

        Bundle bun = this.getIntent().getExtras();

        id = bun.getLong("id");
        String price = bun.getString("price");
        String brand = bun.getString("brand");
        String name = bun.getString("name");

        edName.setText(name);
        edPrice.setText(price);
        edBrand.setText(brand);

        dbSource = new DBSource(this);
        dbSource.open();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditActivity.this.finish();
                Intent i = new Intent(EditActivity.this, ViewActivity.class);
                startActivity(i);
            }
        });
    }

    private void updateData() {
        Inventory inventory = new Inventory();
        inventory.setName(edName.getText().toString().trim());
        inventory.setPrice(edPrice.getText().toString().trim());
        inventory.setBrand(edBrand.getText().toString().trim());
        inventory.setId(id);
        dbSource.updateInventory(inventory);

        Intent i = new Intent(EditActivity.this, ViewActivity.class);

        startActivity(i);

        EditActivity.this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbSource.close();
    }
}