package com.example.daniel.datos_json.Ej1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.daniel.datos_json.City;
import com.example.daniel.datos_json.Ej1.adapter.CityAdapter;
import com.example.daniel.datos_json.R;

import java.util.ArrayList;
import java.util.List;

public class Ej1Activity extends AppCompatActivity {

    public static final String CITY_KEY = "city";

    ListView lv_cities;
    CityAdapter adapter;
    List<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej1);

        lv_cities = (ListView)findViewById(R.id.lv_cities);

        cities = new ArrayList<>();
        cities.add(new City("Malaga", "ES", "2514256"));
        cities.add(new City("Barcelona", "ES", "3128760"));
        cities.add(new City("Valencia", "ES", "2509954"));
        cities.add(new City("Moscow", "RU", "524901"));
        cities.add(new City("Tokyo", "JP", "1850147"));
        cities.add(new City("Baeza", "ES", "2521413"));
        cities.add(new City("Honolulu", "US", "5856195"));

        adapter = new CityAdapter(Ej1Activity.this, cities);
        lv_cities.setAdapter(adapter);
        lv_cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Ej1Activity.this, CityInfo.class);
                intent.putExtra(CITY_KEY, adapter.getItem(i));
                startActivity(intent);
            }
        });
    }
}
