package com.example.aplikasicrudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aplikasicrudsqlite.db.DBSource;
import com.example.aplikasicrudsqlite.models.Inventory;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    ListView listView;

    private DBSource dbSource;

    private ArrayList<Inventory> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        listView = findViewById(R.id.listView);
        dbSource = new DBSource(this);

        dbSource.open();

        values = dbSource.getAllInventory();

        ArrayAdapter<Inventory> adapter = new ArrayAdapter<Inventory>(this,
                android.R.layout.simple_list_item_1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Inventory b = (Inventory) adapterView.getAdapter().getItem(i);

                Inventory edit = dbSource.getInventory(b.getId());

                Intent intent = new Intent(ViewActivity.this, EditActivity.class);
                Bundle bun = new Bundle();

                bun.putLong("id", edit.getId());
                bun.putString("name", edit.getName());
                bun.putString("price", edit.getPrice());
                bun.putString("brand", edit.getBrand());
                intent.putExtras(bun);

                startActivity(intent);

                ViewActivity.this.finish();
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Inventory inventory = (Inventory) adapterView.getAdapter().getItem(i);
                dbSource.deleteInventory(inventory.getId());
                finish();
                Toast.makeText(ViewActivity.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                startActivity(getIntent());
                return true;
            }
        });

    }
}