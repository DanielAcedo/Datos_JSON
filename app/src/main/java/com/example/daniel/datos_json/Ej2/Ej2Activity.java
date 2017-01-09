package com.example.daniel.datos_json.Ej2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.datos_json.Ej1.Ej1Activity;
import com.example.daniel.datos_json.Forecast;
import com.example.daniel.datos_json.R;
import com.example.daniel.datos_json.RestClient;
import com.example.daniel.datos_json.SimpleForecast;
import com.example.daniel.datos_json.WeatherAPI;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Ej2Activity extends AppCompatActivity {

    private TextView txv_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej2);

        txv_textview = (TextView)findViewById(R.id.textview);

        download();
    }

    private void download(){
        String url = WeatherAPI.createCompleteApiCall(
                WeatherAPI.RequestType.LAST_7_DAYS,
                WeatherAPI.APIKEY,
                WeatherAPI.ID_MALAGA,
                WeatherAPI.Metric.UNIT_METRIC
        );

        RestClient.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    SimpleForecast[] forecasts = WeatherAPI.parseJSONSimpleForecast(response);

                    for (SimpleForecast f: forecasts) {

                        txv_textview.setText(txv_textview.getText()+f.toString()+"\n\n");
                    }

                } catch (JSONException e) {
                    Toast.makeText(Ej2Activity.this, "Error al parsear json: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Ej2Activity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(Ej2Activity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
