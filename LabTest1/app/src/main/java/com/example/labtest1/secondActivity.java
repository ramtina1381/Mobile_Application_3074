package com.example.labtest1;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class secondActivity extends AppCompatActivity {


    private DBHelper dbHelper;
    private ListView list;
    ArrayList<String> list_data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(this);

        EdgeToEdge.enable(this);
        setContentView(R.layout.second_activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        list = findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list_data);



        Button b = findViewById(R.id.addBtn);
        b.setOnClickListener(v -> {
            EditText input = findViewById(R.id.input);
            String item = input.getText().toString();
            if (item != null&& item.length()>0){
                list_data.add(item);
                adapter.notifyDataSetChanged();
                input.setText("");
                dbHelper.addItem(item);
            }
        });

        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        list.setOnItemClickListener((parent, view, position, id) -> {
            String clickedItem = list_data.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Item Options");
            builder.setMessage("You selected: " + clickedItem);

            builder.setPositiveButton("Delete", (dialog, which) -> {
                list_data.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(secondActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("Edit", (dialog, which) -> {
                AlertDialog.Builder editBuilder = new AlertDialog.Builder(this);
                editBuilder.setTitle("Edit Item");

                final EditText input = new EditText(this);
                input.setText(clickedItem);
                editBuilder.setView(input);

                editBuilder.setPositiveButton("Save", (editDialog, editWhich) -> {
                    String newItem = input.getText().toString();
                    if (!newItem.isEmpty()) {
                        list_data.set(position, newItem);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(secondActivity.this, "Item updated", Toast.LENGTH_SHORT).show();
                    }
                });
                editBuilder.setNegativeButton("Cancel", (editDialog, editWhich) -> editDialog.dismiss());
                editBuilder.show();
            });
            builder.setNeutralButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
        adapter.notifyDataSetChanged();
    }
}



