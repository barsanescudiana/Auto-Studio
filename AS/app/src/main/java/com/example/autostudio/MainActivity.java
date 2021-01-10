package com.example.autostudio;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView userPic;

    private ListView carList;

    public ArrayList<Car> carArrayList;

    public DatabaseAutoStudio databaseAutoStudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carArrayList = new ArrayList<>();

        databaseAutoStudio = DatabaseAutoStudio.getInstance(getApplicationContext());

//        databaseAutoStudio.getCarsDao().deleteAll();

        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra("User");
        userPic = (ImageView) findViewById(R.id.userPic);

        userPic.setVisibility(View.VISIBLE);

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

        //toolbar
        Button settings = (Button) findViewById(R.id.btn_settings);
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
                bundle.putSerializable("USER", user);
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                profileIntent.putExtras(bundle);
                startActivity(profileIntent);
            }
        });

        //meniu
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

        final CarAdapter adapter = new CarAdapter(getApplicationContext(), R.layout.car_list_item, carArrayList, getLayoutInflater());
        carList.setAdapter(adapter);

        carList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Car selectedCar = carArrayList.get(position);

                databaseAutoStudio.getEventsDao().deleteAll(selectedCar.getCarId());
                databaseAutoStudio.getCarsDao().deleteEventById(selectedCar.getCarId());

                carArrayList.remove(position);
                carArrayList = (ArrayList<Car>) databaseAutoStudio.getCarsDao().getAll();
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        //new JSONTasks().execute();
        new GetCarsAsyncTask().execute();
    }

//    public class JSONTasks extends AsyncTask<String, String, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Log.e("PRE", "On pre execute!!!!1");
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            Log.e("BG", "do in backgroound!!!");
//
//            String result = null;
//            try {
//                URL url = new URL("https://jsonkeeper.com/b/PHBW");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                conn.connect();
//
//                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                    StringBuffer stringBuffer = new StringBuffer();
//                    String temp;
//
//                    while ((temp = bufferedReader.readLine()) != null) {
//                        stringBuffer.append(temp);
//                    }
//
//                    if(stringBuffer.length() == 0) {
//                        return null;
//                    }
//
//                    result = stringBuffer.toString();
//                } else {
//                    result = "error";
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.equals(s);
//
//            JSONObject object = null;
//            try {
//                object = new JSONObject(s);
//                JSONArray array = object.getJSONArray("data");
//
//                for(int i = 0; i<array.length(); i++) {
//                    JSONObject jsonObject = array.getJSONObject(i);
//                    String brand = jsonObject.getString("brand");
//                    String model = jsonObject.getString("model");
//                    String fuel = jsonObject.getString("fuel");
//                    String tank = jsonObject.getString("tank");
//                    String color = jsonObject.getString("color");
//                    double km = jsonObject.getDouble("km");
//                    double avg = jsonObject.getDouble("avg");
//                    int engineCapacity = jsonObject.getInt("capacity");
//                    int engineOutput = jsonObject.getInt("output");
//                    String rca = jsonObject.getString("rca");
//                    String itp = jsonObject.getString("itp");
//
//                    Car car = new Car(brand, model, fuel, km, color, engineCapacity,
//                            engineOutput, avg, new Date(rca), new Date(itp));
//
//                    carArrayList.add(car);
//                }
//
//                Log.e("SUCCES", "Succes getting data!");
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            CarAdapter adapter = new CarAdapter(MainActivity.this, R.layout.car_list_item, carArrayList, getLayoutInflater());
//            carList.setAdapter(adapter);
//        }
//   }

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