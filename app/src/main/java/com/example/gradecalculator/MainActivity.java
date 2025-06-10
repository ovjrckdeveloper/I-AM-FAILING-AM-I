package com.example.gradecalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout ll = findViewById(R.id.ll1);

        TextView createdByTextView = new TextView(this);

        createdByTextView.setText(getString(R.string.developer));
        createdByTextView.setTextColor(Color.GRAY);
        createdByTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 7);

        ll.addView(createdByTextView);
    }

    public void toGradeCalculator(View view){
        Intent intent = new Intent(MainActivity.this, GradeCalculatorActivity.class);
        startActivity(intent);
    }

    public void toCWACalculator(View view){
        Intent intent = new Intent(MainActivity.this, CWACalculatorActivity.class);
        startActivity(intent);
    }
}