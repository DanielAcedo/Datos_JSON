package com.example.daniel.datos_json.Ej4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.datos_json.R;
import com.example.daniel.datos_json.RestClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BufferedHeader;

public class Ej4Activity extends AppCompatActivity {

    private static final String BUSCSV_LINK = "http://datosabiertos.malaga.eu/recursos/transporte/EMT/lineasYHorarios/stops.csv";

    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej4);

        textview = (TextView)findViewById(R.id.textview);

        CSV2JSON converter = new CSV2JSON(getResources().getString(R.string.csv), ",");

        download();

    }

    private void download(){
        RestClient.get(BUSCSV_LINK, new FileAsyncHttpResponseHandler(Ej4Activity.this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(Ej4Activity.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    StringBuffer buffer = new StringBuffer();

                    String line;

                    while((line = reader.readLine()) != null){
                        buffer.append(line+"\n");
                    }

                    reader.close();

                    CSV2JSON converter = new CSV2JSON(buffer.toString(), ",");
                    textview.setText(converter.convert().toString(4));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
