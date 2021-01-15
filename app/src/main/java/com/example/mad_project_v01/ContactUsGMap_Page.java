package com.example.mad_project_v01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class ContactUsGMap_Page extends AppCompatActivity {

    Spinner sptype;
    Button btn_find;
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;

    String cus_name, cus_contact,cus_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_g_map__page);

        cus_name = getIntent().getExtras().getString("name");
        cus_contact = getIntent().getExtras().getString("mobile");
        cus_title = getIntent().getExtras().getString("title");

        Button btn_back = (Button) findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Google map
        btn_find = findViewById(R.id.btn_find);
        sptype = findViewById(R.id.sp_type);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        //Array place type
        final String[] placeType = {"keells", "atm","bank"};
        //Array place name
        String[] palacename = {"Keells", "ATM","Bank"};

        sptype.setAdapter(new ArrayAdapter<>(ContactUsGMap_Page.this, android.R.layout.simple_spinner_dropdown_item, palacename));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(ContactUsGMap_Page.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();

        } else {
            ActivityCompat.requestPermissions(ContactUsGMap_Page.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = sptype.getSelectedItemPosition();

                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + "?location=" + currentLat + "," + currentLong + "&radius=5000" +
                        "&type=" + placeType[i] + "&sensor=true" + "&key=" + getResources().getString(R.string.google_map_key);

                new PlaceTask().execute(url);
            }
        });


    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if(location != null){
                        currentLat = location.getLatitude();
                        currentLong = location.getLongitude();

                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                map = googleMap;
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(currentLat,currentLong),10
                                ));
                            }
                        });
                    }

                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }

    private class PlaceTask extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {
        URL url = new URL(string);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null){
            builder.append(line);
        }
        String data = builder.toString();
        reader.close();
        return data;
    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>>{

        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser = new JsonParser();
            List<HashMap<String,String>> mapList = null;
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            map.clear();
            for(int i=0;i<hashMaps.size();i++){
                HashMap<String,String> hashMapList = hashMaps.get(i);
                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng = Double.parseDouble(hashMapList.get("lng"));
                String name = hashMapList.get("name");

                LatLng latLng = new LatLng(lat,lng);
                MarkerOptions  options = new MarkerOptions();
                options.position(latLng);
                options.title(name);
                map.addMarker(options);

            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactUsGMap_Page.this, MainMenu_Page.class);
        intent.putExtra("title",cus_title);
        intent.putExtra("name", cus_name);
        intent.putExtra("mobile", cus_contact);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
        finish();
    }
}