package com.example.autostudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button newTrip;
    private Button refill;
    private Button docs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newTrip = (Button) findViewById(R.id.newTrip);
        refill = (Button) findViewById(R.id.refill);
        docs = (Button) findViewById(R.id.docs);

        newTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tripIntent = new Intent(getApplicationContext(), TripActivity.class);
                startActivity(tripIntent);
            }
        });

        refill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent refillIntent = new Intent(getApplicationContext(), RefillActivity.class);
                startActivity(refillIntent);
            }
        });

        docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent docsIntent = new Intent(getApplicationContext(), DocsActivity.class);
                startActivity(docsIntent);
            }
        });
    }
}