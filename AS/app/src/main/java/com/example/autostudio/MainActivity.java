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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ImageView userPic;

    private ListView carList;

    public Date itp = new Date(2021, 10, 27);
    public Date rca = new Date(2021, 8, 27);

    public Car testCar = new Car("Renault", "Clio", "Petrol", 102678, "Blue",
            1400, 95, 10.7, itp, rca);
    public Car testCar2 = new Car("Renault", "Clio", "Petrol", 100678, "White",
            1400, 95, 10.7, itp, rca);
    public Car testCar3 = new Car("Renault", "Clio", "Petrol", 100678, "Purple",
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
//        carArrayList.add(testCar);
//        carArrayList.add(testCar2);
//        carArrayList.add(testCar3);

        Button newTrip = (Button) findViewById(R.id.newTrip);
        Button refill = (Button) findViewById(R.id.refill);
        Button docs = (Button) findViewById(R.id.docs);
        Button addCar = (Button) findViewById(R.id.btn_add);


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
                getSupportFragmentManager().beginTransaction().add(R.id.fragment, new RefillFragment()).commit();
            }
        });

        docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent docsIntent = new Intent(getApplicationContext(), DocsActivity.class);
                docsIntent.putExtra("User", user);
                startActivity(docsIntent);

            }
        });

        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCarIntent = new Intent(getApplicationContext(), AddCarActivity1.class);
                addCarIntent.putExtra("user", user);
                startActivity(addCarIntent);
            }
        });

        carList = (ListView) findViewById(R.id.carList);

        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Car selectedCar = carArrayList.get(position);
                Intent carIntent = new Intent(getApplicationContext(), CarActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SELECTED", selectedCar);
                carIntent.putExtras(bundle);
                startActivity(carIntent);
            }
        });
        CarAdapter adapter = new CarAdapter(getApplicationContext(), R.layout.car_list_item, carArrayList, getLayoutInflater());
        carList.setAdapter(adapter);

        new JSONTasks().execute();
    }

    public class JSONTasks extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("PRE", "On pre execute!!!!1");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e("BG", "do in backgroound!!!");

            String result = null;
            try {
                URL url = new URL("https://jsonkeeper.com/b/PHBW");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuffer stringBuffer = new StringBuffer();
                    String temp;

                    while ((temp = bufferedReader.readLine()) != null) {
                        stringBuffer.append(temp);
                    }

                    if(stringBuffer.length() == 0) {
                        return null;
                    }

                    result = stringBuffer.toString();
                } else {
                    result = "error";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.equals(s);

            JSONObject object = null;
            try {
                object = new JSONObject(s);
                JSONArray array = object.getJSONArray("data");

                for(int i = 0; i<array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    String brand = jsonObject.getString("brand");
                    String model = jsonObject.getString("model");
                    String fuel = jsonObject.getString("gas");
                    String tank = jsonObject.getString("tank");
                    String color = jsonObject.getString("color");
                    double km = jsonObject.getDouble("km");
                    double avg = jsonObject.getDouble("avg");
                    int engineCapacity = jsonObject.getInt("capacity");
                    int engineOutput = jsonObject.getInt("output");
                    String rca = jsonObject.getString("rca");
                    String itp = jsonObject.getString("itp");

                    Car car = new Car(brand, model, fuel, km, color, engineCapacity,
                            engineOutput, avg, new Date(rca), new Date(itp));

                    carArrayList.add(car);
                }

                Log.e("SUCCES", "Succes getting data!");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            CarAdapter adapter = new CarAdapter(MainActivity.this, R.layout.car_list_item, carArrayList, getLayoutInflater());
            carList.setAdapter(adapter);
        }
    }
}