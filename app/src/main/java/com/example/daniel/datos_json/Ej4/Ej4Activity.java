package com.example.daniel.datos_json.Ej4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.daniel.datos_json.Ej4.adapter.BusStopAdapter;
import com.example.daniel.datos_json.R;
import com.example.daniel.datos_json.RestClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.Header;

public class Ej4Activity extends AppCompatActivity {

    private static final String BUSCSV_LINK = "http://datosabiertos.malaga.eu/recursos/transporte/EMT/lineasYHorarios/stops.csv";

    public static final String STOP_KEY = "stop";

    private ListView lv_busstops;
    private BusStopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej4);

        lv_busstops = (ListView)findViewById(R.id.busstop_listview);
        lv_busstops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapter != null){
                    BusStop stop = adapter.getItem(i);

                    if(!stop.getUrlInfo().equals("")){
                        Intent intent = new Intent(Ej4Activity.this, BusInfoActivity.class);
                        intent.putExtra(STOP_KEY, stop);

                        startActivity(intent);
                    }
                }
            }
        });

        downloadCSV();

    }

    private void downloadCSV(){
        final ProgressDialog dialog = new ProgressDialog(Ej4Activity.this);
        dialog.setTitle("Descargando fichero...");
        dialog.show();

        RestClient.get(BUSCSV_LINK, new FileAsyncHttpResponseHandler(Ej4Activity.this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(Ej4Activity.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252")); //Read using ANSI Encoding
                    StringBuffer buffer = new StringBuffer();

                    int counter = 0;
                    String line = reader.readLine();

                    while(line != null && counter <= 2000){
                        buffer.append(line);

                        line = reader.readLine();

                        if(line != null && counter <= 2000){
                            buffer.append("\n");
                        }

                        counter++;
                    }

                    reader.close();

                    CSV2JSON converter = new CSV2JSON(buffer.toString(), ",");

                    adapter = new BusStopAdapter(BusUtility.JSONToBusStops(converter.convert()), Ej4Activity.this);
                    lv_busstops.setAdapter(adapter);
                    adapter.sort(BusStop.alphabeticallySort);
                    dialog.dismiss();

                } catch (FileNotFoundException e) {
                    Toast.makeText(Ej4Activity.this, "Error", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(Ej4Activity.this, "Error", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(Ej4Activity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
