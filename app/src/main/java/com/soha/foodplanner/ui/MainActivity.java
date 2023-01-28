package com.soha.foodplanner.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.DatePicker;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.CompleteMeal;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}