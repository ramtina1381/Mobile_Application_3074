package com.example.assignment01;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etHours;
    EditText etRate;
    Button btnCalc;
    int overtimePayHours;
    int payHours;
    int totalHours;
    int rate;
    int pay;
    double overtimePayAmount;  // renamed for clarity
    double totalPay;
    double tax;
    double totalFinalPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // Set the toolbar as the ActionBar

        etHours = findViewById(R.id.hours);
        etRate = findViewById(R.id.rate);
        btnCalc = findViewById(R.id.btnCalc);

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCalc.setOnClickListener(view -> {
            try {
                totalHours = Integer.parseInt(etHours.getText().toString());
                rate = Integer.parseInt(etRate.getText().toString());
                etHours.setText("");
                etRate.setText("");

                if (totalHours > 40) {
                    overtimePayHours = totalHours - 40;
                    payHours = 40;
                } else {
                    overtimePayHours = 0;
                    payHours = totalHours;
                }
                pay = payHours * rate;
                overtimePayAmount = overtimePayHours * rate * 1.5;
                totalPay = pay + overtimePayAmount;
                tax = totalPay * 0.18;
                totalFinalPay = totalPay - tax;

                TextView regularPay = findViewById(R.id.regularPay);
                regularPay.setText(String.valueOf(pay));

                TextView overtimePayText = findViewById(R.id.overtimePay);
                overtimePayText.setText(String.valueOf(overtimePayAmount));

                TextView taxAmount = findViewById(R.id.taxAmount);
                taxAmount.setText(String.valueOf(tax));

                TextView totalPayText = findViewById(R.id.totalPay);
                totalPayText.setText(String.valueOf(totalFinalPay));

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
