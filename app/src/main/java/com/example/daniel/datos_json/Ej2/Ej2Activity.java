package com.example.daniel.datos_json.Ej2;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.datos_json.R;
import com.example.daniel.datos_json.RestClient;
import com.example.daniel.datos_json.SimpleForecast;
import com.example.daniel.datos_json.WeatherAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import cz.msebera.android.httpclient.Header;

public class Ej2Activity extends AppCompatActivity {

    private EditText edt_cityName, edt_countryCode;
    private Button btn_show;
    private TextView txv_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej2);

        txv_textview = (TextView)findViewById(R.id.textview);
        edt_cityName = (EditText)findViewById(R.id.edt_cityName);
        edt_countryCode = (EditText)findViewById(R.id.edt_countryCode);
        btn_show = (Button)findViewById(R.id.btn_show);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txv_textview.setText("");
                download(edt_cityName.getText().toString(), edt_countryCode.getText().toString());
            }
        });
    }

    private void download(String cityName, String countryCode){
        String url = WeatherAPI.createCompleteApiCall(
                WeatherAPI.RequestType.LAST_7_DAYS,
                WeatherAPI.APIKEY,
                cityName,
                countryCode,
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

                    //Save as JSON
                    Gson gson = new GsonBuilder().create();
                    File fileJSON = new File(Environment.getExternalStorageDirectory(),"weather.json");
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileJSON)));
                    JSONObject json = new JSONObject();
                    json.put("forecasts",new JSONArray(gson.toJson(forecasts)));
                    writer.write(json.toString(4));
                    
                    writer.close();

                    Toast.makeText(Ej2Activity.this, "Guardado como weather.json", Toast.LENGTH_SHORT).show();

                    //Save as XML
                    File fileXML = new File(Environment.getExternalStorageDirectory(),"weather.xml");
                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileXML)));
                    json.put("forecasts",new JSONArray(gson.toJson(forecasts)));
                    writer.write(ForecastToXML(forecasts));

                    writer.close();

                    Toast.makeText(Ej2Activity.this, "Guardado como weather.xml", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Toast.makeText(Ej2Activity.this, "Error al parsear json: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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

    private String ForecastToXML(SimpleForecast[] forecasts){
        StringBuffer output = new StringBuffer();

        output.append("<forecasts>");

        for (SimpleForecast forecast: forecasts) {
            output.append("\n\t<forecast>");
            output.append("\n\t\t<date>"+forecast.getFormattedDate()+"</date>");
            output.append("\n\t\t<temperature>");
            output.append("\n\t\t\t<max>"+forecast.getTemp().getMaxTemp()+"</max>");
            output.append("\n\t\t\t<min>"+forecast.getTemp().getMinTemp()+"</min>");
            output.append("\n\t\t</temperature>");
            output.append("\n\t\t<pressure>"+forecast.getPressure()+"</pressure>");
            output.append("\n\t</forecast>\n");
        }

        output.append("\n</forecasts>");

        return output.toString();
    }
}
