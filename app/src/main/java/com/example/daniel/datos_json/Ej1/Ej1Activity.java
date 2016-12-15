package com.example.daniel.datos_json.Ej1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.datos_json.Forecast;
import com.example.daniel.datos_json.MainActivity;
import com.example.daniel.datos_json.R;
import com.example.daniel.datos_json.RestClient;
import com.example.daniel.datos_json.WeatherAPI;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Ej1Activity extends AppCompatActivity {

    private Forecast forecast;
    private ImageView imv;
    private TextView txv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej1);

        txv = (TextView)findViewById(R.id.textview);
        imv = (ImageView)findViewById(R.id.imv_image);

        download();
    }

    private void download(){
        String url = WeatherAPI.createCompleteApiCall(
                WeatherAPI.APIKEY,
                WeatherAPI.ID_MALAGA,
                WeatherAPI.UNIT_METRIC
                );

        RestClient.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Ej1Activity.this.forecast = WeatherAPI.parseJSONForecast(response);
                    txv.setText(forecast.toString());
                    Picasso.with(Ej1Activity.this).load(WeatherAPI.ICON_PREFIX_PATH+forecast.getIconCode()+".png").into(imv);
                } catch (JSONException e) {
                    Toast.makeText(Ej1Activity.this, "Error al parsear json: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Ej1Activity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(Ej1Activity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
