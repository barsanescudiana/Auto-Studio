package com.example.autostudio;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class AddCarActivity1 extends AppCompatActivity {

    ImageView userPic;
    Button addCarNav;
    Toolbar toolbar;
    Car newCar;
    Button next;
    RadioGroup radioGroup;
    RadioButton white, black, grey, red, green, yellow, blue, purple;
    EditText brand, model, fuel, km;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car1);

        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra("User");
        userPic = (ImageView) findViewById(R.id.userPic);

        if (user != null) {
            Glide.with(getApplicationContext()).load(user.getUserPhoto())
                    .centerCrop().circleCrop().into(userPic);
            Log.d("User", user.toString());
        }

        addCarNav = (Button) findViewById(R.id.btn_add);
        addCarNav.setVisibility(View.GONE);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle(R.string.new_car);

        brand = findViewById(R.id.brand_edit);
        model = findViewById(R.id.model_edit);
        fuel = findViewById(R.id.fuel_edit);
        km = findViewById(R.id.km_edit);

        radioGroup = findViewById(R.id.radioGroup);

        black = findViewById(R.id.colorBlack);
        white = findViewById(R.id.colorWhite);
        grey = findViewById(R.id.colorGrey);
        red = findViewById(R.id.colorRed);
        green = findViewById(R.id.colorGreen);
        yellow = findViewById(R.id.colorYellow);
        blue = findViewById(R.id.colorBlue);
        purple = findViewById(R.id.colorPurple);

        next = findViewById(R.id.next);

        Button settings = (Button) findViewById(R.id.btn_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });
        Button dashboard = (Button) findViewById(R.id.btn_main);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashboardIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(dashboardIntent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                newCar = new Car();

                if(black.isChecked()) {
                    newCar.setColor("Black");
                    Toast.makeText(getApplicationContext(), "selected: Black", Toast.LENGTH_SHORT).show();
                }

                if(white.isChecked()) {
                    newCar.setColor("White");
                    Toast.makeText(getApplicationContext(), "selected: White", Toast.LENGTH_SHORT).show();
                }

                if(grey.isChecked()) {
                    newCar.setColor("Grey");
                    Toast.makeText(getApplicationContext(), "selected: Grey", Toast.LENGTH_SHORT).show();
                    grey.setForeground(getResources().getDrawable(R.drawable.checked));
                }

                if(red.isChecked()) {
                    newCar.setColor("Red");
                    Toast.makeText(getApplicationContext(), "selected: Red", Toast.LENGTH_SHORT).show();
                    red.setForeground(getResources().getDrawable(R.drawable.checked));
                }

                if(green.isChecked()) {
                    newCar.setColor("Green");
                    Toast.makeText(getApplicationContext(), "selected: Green", Toast.LENGTH_SHORT).show();
                    green.setForeground(getResources().getDrawable(R.drawable.checked));
                }

                if(yellow.isChecked()) {
                    newCar.setColor("Yellow");
                    Toast.makeText(getApplicationContext(), "selected: Yellow", Toast.LENGTH_SHORT).show();
                    yellow.setForeground(getResources().getDrawable(R.drawable.checked));
                }

                if(blue.isChecked()) {
                    newCar.setColor("Blue");
                    Toast.makeText(getApplicationContext(), "selected: Blue", Toast.LENGTH_SHORT).show();
                    blue.setForeground(getResources().getDrawable(R.drawable.checked));
                }

                if(purple.isChecked()) {
                    newCar.setColor("Purple");
                    Toast.makeText(getApplicationContext(), "selected: Purple", Toast.LENGTH_SHORT).show();
                    purple.setForeground(getResources().getDrawable(R.drawable.checked));
                }

                newCar.setBrand(brand.getText().toString());
                newCar.setModel(model.getText().toString());
                newCar.setFuel(fuel.getText().toString());
                newCar.setKm(Double.parseDouble(km.getText().toString()));

                Bundle bundle = new Bundle();
                bundle.putSerializable("NEW", newCar);

                Intent nextAdd = new Intent(getApplicationContext(), AddCarActivity2.class);
                nextAdd.putExtras(bundle);
                startActivity(nextAdd);
            }
        });

    }

}