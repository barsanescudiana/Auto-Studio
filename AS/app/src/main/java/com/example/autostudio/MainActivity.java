package com.example.autostudio;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private ImageView userPic;

    private Button newTrip;
    private Button refill;
    private Button docs;

    private ListView carList;

    public Date itp = new Date(2021, 10, 27);
    public Date rca = new Date(2021, 8, 27);

    public Car testCar = new Car("Renault", "Clio", "Petrol", 100678, "Blue",
            1400, 95, 10.7, itp, rca);
    public Car testCar2 = new Car("Renault", "Clio", "Petrol", 100678, "Blue",
            1400, 95, 10.7, itp, rca);
    public Car testCar3 = new Car("Renault", "Clio", "Petrol", 100678, "Blue",
            1400, 95, 10.7, itp, rca);

    public ArrayList<Car> carArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra("User");
        userPic = (ImageView) findViewById(R.id.userPic);

        if (user != null) {
            Glide.with(getApplicationContext()).load(user.getUserPhoto())
                    .centerCrop().circleCrop().into(userPic);
            Log.d("User", user.toString());
        }


        carArrayList.add(testCar);
        carArrayList.add(testCar2);
        carArrayList.add(testCar3);

        newTrip = (Button) findViewById(R.id.newTrip);
        refill = (Button) findViewById(R.id.refill);
        docs = (Button) findViewById(R.id.docs);

        newTrip.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
//                Intent tripIntent = new Intent(getApplicationContext(), TripActivity.class);
//                startActivity(tripIntent);
//              //mainframelayout.getForeground().setAlpha(220)
                getSupportFragmentManager().beginTransaction().add(R.id.fragment, new NewTripFragment()).commit();
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
                Date current = new Date();
                Log.e("ITP", "\n" + testCar.getExpDateITP().toString() + "\n" + current.toString());
                Log.e("\n\nRCA",  "\n" + testCar.getExpDateRCA().toString() + "\n" + current.toString());
            }
        });


        carList = (ListView) findViewById(R.id.carList);

        CarAdapter adapter = new CarAdapter(getApplicationContext(), R.layout.car_list_item, carArrayList, getLayoutInflater());
        carList.setAdapter(adapter);

//        Window window = this.getWindow();
//
//// clear FLAG_TRANSLUCENT_STATUS flag:
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
//// finally change the color
//        window.setStatusBarColor(ContextCompat.getColor(this, R.color.toolbarTitle));

    }
}