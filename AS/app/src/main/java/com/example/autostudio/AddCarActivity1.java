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
        userPic = findViewById(R.id.userPic);

        if (user != null) {
            Glide.with(getApplicationContext()).load(user.getUserPhoto())
                    .centerCrop().circleCrop().into(userPic);
        }

        addCarNav = findViewById(R.id.btn_add);
        addCarNav.setVisibility(View.GONE);
        toolbar = findViewById(R.id.toolbar2);
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

        Button settings = findViewById(R.id.btn_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });
        Button dashboard = findViewById(R.id.btn_main);
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
                }

                if(white.isChecked()) {
                    newCar.setColor("White");
                }

                if(grey.isChecked()) {
                    newCar.setColor("Grey");
                    grey.setForeground(getResources().getDrawable(R.drawable.checked));
                }

                if(red.isChecked()) {
                    newCar.setColor("Red");
                    red.setForeground(getResources().getDrawable(R.drawable.checked));
                }

                if(green.isChecked()) {
                    newCar.setColor("Green");
                    green.setForeground(getResources().getDrawable(R.drawable.checked));
                }

                if(yellow.isChecked()) {
                    newCar.setColor("Yellow");
                    yellow.setForeground(getResources().getDrawable(R.drawable.checked));
                }

                if (blue.isChecked()) {
                    newCar.setColor("Blue");
                    blue.setForeground(getResources().getDrawable(R.drawable.checked));
                }

                if (purple.isChecked()) {
                    newCar.setColor("Purple");
                    purple.setForeground(getResources().getDrawable(R.drawable.checked));
                }

                if (brand.getText().length() == 0)
                    brand.setError("Brand is required!");
                else newCar.setBrand(brand.getText().toString());

                if (model.getText().length() == 0)
                    model.setError("Model is required");
                else newCar.setModel(model.getText().toString());

                if (fuel.getText().length() == 0)
                    fuel.setError("Fuel is required");
                else newCar.setFuel(fuel.getText().toString());

                if (km.getText().length() == 0)
                    km.setError("KM is required!");
                else newCar.setKm(Double.parseDouble(km.getText().toString()));


//                newCar.setBrand(brand.getText().length() == 0 ? brand.setError("Brand is required") : brand.getText().toString());
//                newCar.setModel(model.getText().toString());
//                newCar.setFuel(fuel.getText().toString());
//                newCar.setKm(Double.parseDouble(km.getText().toString()));

                Bundle bundle = new Bundle();
                bundle.putSerializable("NEW", newCar);

                Intent nextAdd = new Intent(getApplicationContext(), AddCarActivity2.class);
                nextAdd.putExtras(bundle);
                startActivity(nextAdd);
            }
        });

    }

}