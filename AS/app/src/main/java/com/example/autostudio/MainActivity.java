package com.example.autostudio;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.autostudio.adapters.CarAdapter;
import com.example.autostudio.classes.Car;
import com.example.autostudio.fragments.NewTripFragment;
import com.example.autostudio.fragments.RefillFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView userPic;
    private Button weather;

    private ListView carList;

    public ArrayList<Car> carArrayList;

    public DatabaseAutoStudio databaseAutoStudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carArrayList = new ArrayList<>();

        databaseAutoStudio = DatabaseAutoStudio.getInstance(getApplicationContext());

        final Intent intent = getIntent();

        final User user = (User) intent.getSerializableExtra("User");
        userPic = findViewById(R.id.userPic);

        if (user != null) {
            Glide.with(getApplicationContext()).load(user.getUserPhoto())
                    .centerCrop().circleCrop().into(userPic);
        }

        Button newTrip = findViewById(R.id.newTrip);
        Button refill = findViewById(R.id.refill);
        Button docs = findViewById(R.id.docs);
        Button addCar = findViewById(R.id.btn_add);
        weather = findViewById(R.id.docs2);

        Button settings = findViewById(R.id.btn_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });
        findViewById(R.id.btn_main).setVisibility(View.GONE);


        final Bundle bundle = new Bundle();
        userPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putSerializable("USER_EMAIL", user.getUserEmail());
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                profileIntent.putExtras(bundle);
                startActivity(profileIntent);
            }
        });

        newTrip.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
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
                Intent docsIntent = new Intent(getApplicationContext(), ReportsActivity.class);
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

        carList = findViewById(R.id.carList);

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

        final CarAdapter adapter = new CarAdapter(getApplicationContext(), R.layout.car_list_item, carArrayList, getLayoutInflater());
        carList.setAdapter(adapter);

        carList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Car carToDelete = carArrayList.get(position);

                final CarAdapter carAdapter = (CarAdapter) carList.getAdapter();

                final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).
                        setTitle("Deleting a car").
                        setMessage("Dou you want to delete this car?").
                        setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Nothing changed", Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        }).
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                carArrayList.remove(carToDelete);
                                carAdapter.notifyDataSetChanged();

                                databaseAutoStudio.getEventsDao().deleteAllByCarId(carToDelete.getCarId());
                                databaseAutoStudio.getCarsDao().deleteEventById(carToDelete.getCarId());

                                Toast.makeText(getApplicationContext(), "Successfully deleted", Toast.LENGTH_LONG).show();

                            }
                }).create();

                dialog.show();

                return true;
            }
        });

        new JSONTasks().execute();
        new GetCarsAsyncTask().execute();
    }

    public class JSONTasks extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=44.4268&lon=26.1025&units=metric&appid=6213eee9274adc0299c1a5df35a3611d");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    StringBuffer sbuf = new StringBuffer();
                    String temp;

                    while ((temp = br.readLine()) != null) {
                        sbuf.append(temp);
                    }

                    if (sbuf.length() == 0) {
                        return null;
                    }

                    result = sbuf.toString();
                } else result = "error";

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(s);

                JSONObject main = (JSONObject) jsonObject.get("main");

                if (main.getDouble("temp") >= 4) {
                    weather.setBackgroundResource(R.drawable.weather_summer);
                } else {
                    weather.setBackgroundResource(R.drawable.weather_winter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private class GetCarsAsyncTask extends AsyncTask<Void, Void, List<Car>> {

        @Override
        protected List<Car> doInBackground(Void... voids) {
            return databaseAutoStudio.getCarsDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Car> cars) {
            super.onPostExecute(cars);

            carArrayList = (ArrayList<Car>) cars;

            CarAdapter adapter = new CarAdapter(MainActivity.this, R.layout.car_list_item, carArrayList, getLayoutInflater());
            carList.setAdapter(adapter);
        }
    }
}