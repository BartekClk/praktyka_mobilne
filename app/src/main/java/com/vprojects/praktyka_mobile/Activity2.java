package com.vprojects.praktyka_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Activity2 extends AppCompatActivity {

    private ArrayList<String> shoppingList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        ((TextView) findViewById(R.id.name)).setText(name);

        int textSize = intent.getIntExtra("textSize", 0);
        ((TextView) findViewById(R.id.name)).setTextSize(textSize);
        ((TextView) findViewById(R.id.name_)).setTextSize(textSize);
        ((TextView) findViewById(R.id.title)).setTextSize(textSize);

        EditText newItem = (EditText) findViewById(R.id.editText);
        newItem.setTextSize(textSize);

        Button add = findViewById(R.id.add);
        Button closeList = findViewById(R.id.closeList);
        ((Button) findViewById(R.id.add)).setTextSize(textSize);
        ((Button) findViewById(R.id.closeList)).setTextSize(textSize);

        ListView listView = findViewById(R.id.listView);

        shoppingList = new ArrayList<>();
        shoppingList.add("Masło");
        shoppingList.add("Chleb");
        shoppingList.add("Mąka");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingList);

        listView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItemText = newItem.getText().toString().trim();
                if (!newItemText.isEmpty()) {
                    addItemToList(newItemText);
                    newItem.setText("");
                }
            }
        });

        closeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void addItemToList(String item) {
        shoppingList.add(item);
        adapter.notifyDataSetChanged();
    }
}