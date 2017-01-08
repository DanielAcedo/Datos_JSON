package com.example.daniel.datos_json.Ej4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.daniel.datos_json.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Ej4Activity extends AppCompatActivity {

    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej4);

        textview = (TextView)findViewById(R.id.textview);

        CSV2JSON converter = new CSV2JSON(getResources().getString(R.string.csv), ",");

        try {
            JSONObject busdata = converter.convert();

            textview.setText(busdata.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
