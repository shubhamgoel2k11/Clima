package com.personalapplication.clima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ImageView imageView;
    TextView country, city, temp, Time, latitude, longitude, humidity, sunrise, sunset, pressure, windspeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText= findViewById(R.id.editTextTextPersonName);
        button= findViewById(R.id.button);
        country= findViewById(R.id.country);
        city= findViewById(R.id.city);
        temp= findViewById(R.id.Temperature);
        imageView= findViewById(R.id.imageButton4);
        Time= findViewById(R.id.time);
        latitude= findViewById(R.id.Latitude);
        longitude= findViewById(R.id.Longitude);
        humidity= findViewById(R.id.Humidity);
        sunrise= findViewById(R.id.Sunrise);
        sunset= findViewById(R.id.Sunset);
        pressure= findViewById(R.id.Pressure);
        windspeed= findViewById(R.id.WindSpeed);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findWeather();
            }
        });
    }

        public void findWeather(){

        String city_url= editText.getText().toString();
        String url="http://api.openweathermap.org/data/2.5/weather?q="+city_url+"&appid=39f77bd471f52b076fde7c1dd29e8d78";

            StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject= new JSONObject(response);

                        JSONObject object1= jsonObject.getJSONObject("sys");
                        String country_find= object1.getString("country");
                        country.setText(country_find);

                        String city_find= jsonObject.getString("name");
                        city.setText(city_find);

                        JSONObject object2= jsonObject.getJSONObject("main");
                        Double temp_find= object2.getDouble("temp");
                        temp.setText(temp_find+"°F");

                        JSONArray jsonArray= jsonObject.getJSONArray("weather");
                        JSONObject obj= jsonArray.getJSONObject(0);
                        String icon= obj.getString("icon");
                        Picasso.get().load("http://openweathermap.org/img/wn/"+icon+"@2x.png").resize(400,400).into(imageView);

                        Calendar calendar= Calendar.getInstance();
                        SimpleDateFormat std= new SimpleDateFormat("dd/MM/yyyy \nHH:mm:ss");
                        String date= std.format(calendar.getTime());
                        Time.setText(date);

                        JSONObject object3= jsonObject.getJSONObject("coord");
                        Double lat_find= object3.getDouble("lat");
                        latitude.setText(lat_find+" N");

                        JSONObject object4= jsonObject.getJSONObject("coord");
                        Double long_find= object4.getDouble("lon");
                        longitude.setText(long_find+" E");

                        JSONObject object5= jsonObject.getJSONObject("main");
                        int hum_find= object5.getInt("humidity");
                        humidity.setText(hum_find+" %");

                        JSONObject object6= jsonObject.getJSONObject("sys");
                        String rise_find= object6.getString("sunrise");
                        sunrise.setText(rise_find);

                        JSONObject object7= jsonObject.getJSONObject("sys");
                        String set_find= object7.getString("sunset");
                        sunset.setText(set_find);

                        JSONObject object8= jsonObject.getJSONObject("main");
                        String pressure_find= object8.getString("pressure");
                        pressure.setText(pressure_find+" hPa");

                        JSONObject object9= jsonObject.getJSONObject("wind");
                        String wind_find= object9.getString("speed");
                        windspeed.setText(wind_find+" km/h");






                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }
}