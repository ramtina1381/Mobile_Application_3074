package com.example.lab5;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private final static String KEY_NAME = "name";
    private DBHelper dbHelper;
    private ListView list;
    String[] data = {"one", "two", "three"};
    ArrayList<String> list_data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(this);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        for(int i=0; i<data.length; i++){
            list_data.add(data[i]);
        }
        list = findViewById(R.id.list);
        preferences = getPreferences(Context.MODE_PRIVATE);
        // SAVE VALUE
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NAME, "Ramtin Abolfazli");
        editor.apply();

        // READ VALUE
        String name = preferences.getString(KEY_NAME, "---Not Set---");
        TextView txt_name = findViewById(R.id.name);
        txt_name.setText(name);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list_data);

        Button b = findViewById(R.id.button);
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
        list_data.add("New Item");
        adapter.notifyDataSetChanged();

        list.setOnItemClickListener((parent, view, position, id) -> {
            String item = list_data.get(position);
            view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                @Override
                public void run() {
                    list_data.remove(item);
                    adapter.notifyDataSetChanged();
                    view.setAlpha(1);
                }
            });
        });
    }
}