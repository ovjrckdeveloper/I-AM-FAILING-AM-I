package com.example.gradecalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CWACalculatorActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    TextView lblGWA;
    Button btnAdd, btnCalculate;

    int gwaCounter = 1;
    ArrayList<EditText> gwaFields = new ArrayList<>();
    ArrayList<LinearLayout> gwaContainers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cwacalculator_activity);

        linearLayout = findViewById(R.id.linearLayout);
        lblGWA = findViewById(R.id.lblGWA);
        btnAdd = findViewById(R.id.btnAdd);
        btnCalculate = findViewById(R.id.btnCalculate);

        // Add first field to array
        EditText firstField = findViewById(R.id.gwa1);
        gwaFields.add(firstField);

        btnAdd.setOnClickListener(v -> addGwaField());

        btnCalculate.setOnClickListener(v -> calculateGWA());
    }

    private void addGwaField() {
        gwaCounter++;

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setPadding(0, 10, 0, 10);

        EditText gwaField = new EditText(this);
        gwaField.setId(View.generateViewId());
        gwaField.setHint("GWA");
        gwaField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        gwaField.setTextColor(Color.WHITE);
        gwaField.setHintTextColor(Color.GRAY);
        gwaField.setTextSize(20);
        gwaField.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        gwaFields.add(gwaField);

        Button btnDelete = new Button(this);
        btnDelete.setText("X");
        btnDelete.setBackgroundColor(Color.RED);
        btnDelete.setTextColor(Color.WHITE);

        btnDelete.setOnClickListener(view -> {
            linearLayout.removeView(container);
            gwaFields.remove(gwaField);
            gwaContainers.remove(container);
            calculateGWA();
        });

        container.addView(gwaField);
        container.addView(btnDelete);

        linearLayout.addView(container);
        gwaContainers.add(container);
    }

    private void calculateGWA() {
        double total = 0;
        int count = 0;

        for (EditText field : gwaFields) {
            String input = field.getText().toString().trim();
            if (!input.isEmpty()) {
                try {
                    double grade = Double.parseDouble(input);
                    total += grade;
                    count++;
                } catch (NumberFormatException e) {
                    field.setError("Invalid number");
                    return;
                }
            }
        }

        if (count == 0) {
            lblGWA.setText("GWA: --");
            lblGWA.setTextColor(Color.WHITE);
            return;
        }

        double gwa = total / count;
        lblGWA.setText(String.format("GWA: %.2f", gwa));

        // Apply grading color
        if (gwa >= 1.0 && gwa <= 1.5) {
            lblGWA.setTextColor(Color.parseColor("#00FF00")); // Green
        } else if (gwa > 1.5 && gwa <= 2.5) {
            lblGWA.setTextColor(Color.YELLOW);
        } else if (gwa > 2.5 && gwa <= 3.0) {
            lblGWA.setTextColor(Color.parseColor("#FFA500")); // Orange
        } else {
            lblGWA.setTextColor(Color.RED); // Failing
        }
    }
}
