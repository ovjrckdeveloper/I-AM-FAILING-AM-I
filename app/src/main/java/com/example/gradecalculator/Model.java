package com.example.gradecalculator;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;

public class Model {

    // H O L D S   T H E   D A T A    O F   U S E R
    public static List<String> course = new ArrayList<>();
    public static List<Float> prelim = new ArrayList<>();
    public static List<Float> midterm = new ArrayList<>();
    public static List<Float> prefi = new ArrayList<>();
    public static List<Float> finals = new ArrayList<>();


    // H O L D S   V I E W
    public static List<Button> buttons = new ArrayList<>();
    public static List<TextView> textViews = new ArrayList<>();
    public static List<EditText> editTexts = new ArrayList<>();
}
