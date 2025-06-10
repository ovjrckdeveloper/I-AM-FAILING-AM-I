package com.example.gradecalculator;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.InputType;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

public class GradeCalculatorActivity extends AppCompatActivity {

    int counter = 1;
    LinearLayout linearLayout;
    TextView lblGWA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grade_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Model.course.clear();
        Model.prelim.clear();
        Model.midterm.clear();
        Model.prefi.clear();
        Model.finals.clear();
        Model.buttons.clear();
        Model.editTexts.clear();
        Model.textViews.clear();

        linearLayout = findViewById(R.id.linearLayout);
        lblGWA = findViewById(R.id.lblGWA);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnCalculate = findViewById(R.id.btnCalculate);

        btnAdd.setOnClickListener(v -> addCourseHolder());
        btnCalculate.setOnClickListener(v -> calculateGrades());
    }

    private void addCourseHolder() {
        counter++;

        LinearLayout courseHolder = new LinearLayout(this);
        courseHolder.setOrientation(LinearLayout.VERTICAL);
        courseHolder.setPadding(10, 10, 10, 10);

        TextView label = new TextView(this);
        label.setText("COURSE DATA");
        label.setTextColor(Color.WHITE);
        label.setTextSize(25);

        EditText courseName = new EditText(this);
        courseName.setHint("Course Name");
        styleEditText(courseName);

        EditText prelim = new EditText(this);
        prelim.setHint("Prelim Grade");
        prelim.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        styleEditText(prelim);

        EditText midterm = new EditText(this);
        midterm.setHint("Midterm Grade");
        midterm.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        styleEditText(midterm);

        EditText prefi = new EditText(this);
        prefi.setHint("Prefinal Grade");
        prefi.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        styleEditText(prefi);

        EditText finals = new EditText(this);
        finals.setHint("Final Grade");
        finals.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        styleEditText(finals);

        Button btnDelete = new Button(this);
        btnDelete.setText("Delete");
        btnDelete.setTextSize(20);
        btnDelete.setTextColor(Color.WHITE);
        btnDelete.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F63030")));
        btnDelete.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView result = new TextView(this);
        result.setTextColor(Color.WHITE);
        result.setTextSize(18);
        result.setVisibility(View.GONE);

        courseHolder.addView(label);
        courseHolder.addView(courseName);
        courseHolder.addView(prelim);
        courseHolder.addView(midterm);
        courseHolder.addView(prefi);
        courseHolder.addView(finals);
        courseHolder.addView(btnDelete);
        courseHolder.addView(result);

        Model.editTexts.add(courseName);
        Model.editTexts.add(prelim);
        Model.editTexts.add(midterm);
        Model.editTexts.add(prefi);
        Model.editTexts.add(finals);
        Model.buttons.add(btnDelete);
        Model.textViews.add(result);

        btnDelete.setOnClickListener(v -> {
            linearLayout.removeView(courseHolder);
            Model.editTexts.removeAll(Arrays.asList(courseName, prelim, midterm, prefi, finals));
            Model.buttons.remove(btnDelete);
            Model.textViews.remove(result);
        });

        linearLayout.addView(courseHolder);
    }

    private void calculateGrades() {
        float gwaTotal = 0;
        int subjectCount = 0;

        lblGWA.setText("GWA: -");
        lblGWA.setTextColor(Color.WHITE);

        int fieldsPerCourse = 5;

        for (int i = 0, j = 0; i < Model.textViews.size(); i++, j += fieldsPerCourse) {
            try {
                EditText courseName = Model.editTexts.get(j);
                EditText prelim = Model.editTexts.get(j + 1);
                EditText midterm = Model.editTexts.get(j + 2);
                EditText prefi = Model.editTexts.get(j + 3);
                EditText finals = Model.editTexts.get(j + 4);

                String course = courseName.getText().toString().trim();
                float pre = Float.parseFloat(prelim.getText().toString().trim());
                float mid = Float.parseFloat(midterm.getText().toString().trim());
                float prefiGrade = Float.parseFloat(prefi.getText().toString().trim());
                float fin = Float.parseFloat(finals.getText().toString().trim());

                float avg = ((pre + mid + prefiGrade) * 0.20f) + (fin * 0.40f);
                float finalGrade = getFinalGrade(avg);

                String resultText = String.format("FINAL AVG: %.2f / %.2f", avg, finalGrade);
                TextView resultView = Model.textViews.get(i);
                resultView.setText(resultText);
                resultView.setTextSize(20);
                resultView.setVisibility(View.VISIBLE);

                if (finalGrade <= 3.00f) {
                    resultView.setTextColor(Color.GREEN);
                } else {
                    resultView.setTextColor(Color.RED);
                }

                gwaTotal += finalGrade;
                subjectCount++;

            } catch (Exception e) {
                Model.textViews.get(i).setText("Invalid or incomplete input.");
                Model.textViews.get(i).setTextColor(Color.RED);
            }
        }

        if (subjectCount > 0) {
            float gwa = gwaTotal / subjectCount;
            lblGWA.setText(String.format("GWA: %.2f", gwa));
            if (gwa <= 3.00f) {
                lblGWA.setTextColor(Color.GREEN);
            } else {
                lblGWA.setTextColor(Color.RED);
            }
        } else {
            Toast.makeText(this, "Please enter at least one complete course input.", Toast.LENGTH_SHORT).show();
        }
    }

    private float getFinalGrade(float avg) {
        if (avg >= 97.50f) return 1.00f;
        else if (avg >= 94.50f) return 1.25f;
        else if (avg >= 91.50f) return 1.50f;
        else if (avg >= 88.50f) return 1.75f;
        else if (avg >= 85.50f) return 2.00f;
        else if (avg >= 81.50f) return 2.25f;
        else if (avg >= 77.50f) return 2.50f;
        else if (avg >= 73.50f) return 2.75f;
        else if (avg >= 69.50f) return 3.00f;
        else return 5.00f;
    }

    private void styleEditText(EditText editText) {
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setTextColor(Color.WHITE);
        editText.setHintTextColor(Color.parseColor("#717070"));
        editText.setTextSize(20);
        editText.setGravity(Gravity.CENTER);
    }
}
