package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Integer focusIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        cityList = findViewById(R.id.city_list);
        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        setCityListListeners();

        View addButton = findViewById(R.id.add_button);
        View deleteButton = findViewById(R.id.delete_button);
        TextView textInput = findViewById(R.id.city_input);

        deleteButton.setOnClickListener((view) -> {
            if (focusIndex != null) {
                dataList.remove((int) focusIndex);
                setCityListListeners();
                resetFocus();
            }
        });

        addButton.setOnClickListener((view) -> {
            dataList.add(textInput.getText().toString());
            setCityListListeners();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void resetFocus() {
        if (focusIndex != null) {
            View focusItem = cityList.getChildAt(focusIndex);
            focusItem.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            focusIndex = null;
        }
    }

    private void focusCity(Integer index) {
        View focusItem = cityList.getChildAt(index);
        focusItem.setBackgroundColor(ContextCompat.getColor(this, R.color.grey));
        focusIndex = index;
    }

    private void setCityListListeners() {
        cityAdapter.notifyDataSetChanged();
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            if (focusIndex != null) {
                // If focusIndex == position, then an already focused city has been clicked. Remove it from focus.
                // if focusIndex is not null, and != position, then remove the focused city from focus. Focus the clicked city.

                if (focusIndex == position) {
                    resetFocus();
                } else {
                    resetFocus();
                    focusCity(position);
                }
            } else {
                // If focusIndex is null, then focus the clicked city.
                focusCity(position);
            }
        });
    }
}