package com.vprojects.praktyka_mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_IS_FIRST_RUN = "isFirstRun";

    private int textSize = 20;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean(KEY_IS_FIRST_RUN, true);

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch sw = (Switch) findViewById(R.id.switch1);
        TextView darkMode = (TextView) findViewById(R.id.darkMode);

        if (isFirstRun) {
            int currentNighMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch(currentNighMode){
                case Configuration.UI_MODE_NIGHT_NO:
                    sw.setChecked(false);
                    darkMode.setText(R.string.darkMode);
                    break;

                case Configuration.UI_MODE_NIGHT_YES:
                    sw.setChecked(true);
                    darkMode.setText(R.string.lightMode);
                    break;
            }
        }

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    darkMode.setText(R.string.lightMode);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    darkMode.setText(R.string.darkMode);
                }
            }
        });

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMin(10);
        seekBar.setMax(30);
        seekBar.setProgress(textSize);

        final TextView[] changeSize = {(TextView) findViewById(R.id.changeSize)};
        final String[] changeSizeText = {getString(R.string.changeSize) + " "+String.valueOf(textSize)};
        changeSize[0].setText(changeSizeText[0]);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int pro = 20;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pro = progress;
                String text = getString(R.string.changeSize)+" "+Integer.toString(pro);
                changeSize[0].setText(text);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textSize = pro;
                ((TextView) findViewById(R.id.changeSize)).setTextSize(pro);
                ((TextView) findViewById(R.id.darkMode)).setTextSize(pro);
                ((EditText) findViewById(R.id.provideName)).setTextSize(pro);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TextView provideName = (TextView) findViewById(R.id.provideName);
        String provideNameText = provideName.getText().toString();
        if (provideNameText.isEmpty()){
            provideName.setHint(getString(R.string.haveToProvideName));
        }else {
            Intent intent = new Intent(this, Activity2.class);
            intent.putExtra("name", provideNameText);
            intent.putExtra("textSize", textSize);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}