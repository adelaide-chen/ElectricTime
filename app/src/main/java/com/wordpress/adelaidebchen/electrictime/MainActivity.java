package com.wordpress.adelaidebchen.electrictime;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static List<Transport> MODES = Arrays.asList(
            new Transport("Walking", 3.1, 30),
            new Transport("Boosted Mini S Board", 18, 7),
            new Transport("Evolve Skateboard", 26,31),
            new Transport("MotoTec Skateborad", 22, 10),
            new Transport("OneWheel", 19, 7),
            new Transport("Segway Ninebot One S1", 12.5, 15),
            new Transport("GeoBlade 500", 15, 8),
            new Transport("Hovertrax Hoverboard", 8 ,8),
            new Transport("Segway i2 SE", 12.5, 24),
            new Transport("Razor Scooter", 10, 7)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics box = getResources().getDisplayMetrics();
        if (box.widthPixels > box.heightPixels) {
            setContentView(R.layout.activity_main_landscape);
        } else {
            setContentView(R.layout.activity_main);
        }

        initModeList();
        buttonHandler();
    }

    private void initModeList() {
        // Extract mode name from the list above
        List<String> modeList = new ArrayList<>();
        for (Transport type : MODES) {
            modeList.add(type.getMode());
        }

        // set selections for Spinner/Drop down list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, modeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
    }

    private void buttonHandler() {
        // event handling for button clicking
        final Button button = findViewById(R.id.button_id);
        final EditText number = findViewById(R.id.miles);
        final Spinner spinner = findViewById(R.id.spinner);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String str = number.getText().toString();
                // No input in number field
                if (str.length() == 0) {
                    number.setHint(R.string.no_miles);
                    number.setHintTextColor(Color.RED);
                    return;
                }
                final int miles = Integer.parseInt(str);
                final String mode = spinner.getSelectedItem().toString();
                int id = (int)spinner.getSelectedItemId();
                final String resStr = primaryLogic(miles, id, mode);

                final TextView result = findViewById(R.id.result);
                result.setText(resStr);

                secondaryLogic(miles, id);
            }
        });
    }

    @NonNull
    private String primaryLogic(int miles, int id, String mode) {
        double speed = MODES.get(id).getSpeed();
        int range = MODES.get(id).getRange();
        if (range < miles) {
            return getString(R.string.out_of_range, range);
        } else {
            double minutes = miles/speed * 60;
            return getString(R.string.result, getString(R.string.leading), minutes, mode);
        }
    }

    private void secondaryLogic(int miles, int id) {
        LinearLayout alternate = findViewById(R.id.listView);
        alternate.removeAllViews();
        for (Transport mode : MODES) {
            int i = MODES.indexOf(mode);
            String str = primaryLogic(miles, i, mode.getMode());
            String leadStr = getString(R.string.leading);
            if (str.startsWith(leadStr) && i != id ) {
                TextView textView = new TextView(this);
                str = str.substring(leadStr.length());
                textView.setText(str);
                alternate.addView(textView);
            }
        }
        if (alternate.getChildCount() == 0) {
            TextView textView = new TextView(this);
            textView.setText(getString(R.string.no_alter));
            alternate.addView(textView);
        }
    }
}

