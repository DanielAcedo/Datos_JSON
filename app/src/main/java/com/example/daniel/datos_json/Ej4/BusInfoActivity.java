package com.example.daniel.datos_json.Ej4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.datos_json.R;
import com.example.daniel.datos_json.RestClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class BusInfoActivity extends AppCompatActivity {

    private BusStop stop;

    private TextView txv_title;
    private TextView txv_businfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        txv_title = (TextView)findViewById(R.id.txv_title);
        txv_businfo = (TextView)findViewById(R.id.txv_businfo);

        getIntentBusStop();
        downloadBusInfoFile();
    }

    private void getIntentBusStop(){
        Intent intent = getIntent();

        if(intent.getParcelableExtra(Ej4Activity.STOP_KEY) != null){
            stop = intent.getParcelableExtra(Ej4Activity.STOP_KEY);
            txv_title.setText(stop.getName());
        }
    }

    private void downloadBusInfoFile(){
        final ProgressDialog dialog = new ProgressDialog(BusInfoActivity.this);
        dialog.setTitle("Descargando información...");
        dialog.show();

        RestClient.get(stop.getUrlInfo(), new FileAsyncHttpResponseHandler(BusInfoActivity.this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                dialog.dismiss();
                Toast.makeText(BusInfoActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                BusStopInfo info = null;
                dialog.dismiss();
                
                try {
                    info = BusUtility.getInfoFromStop(file);

                    StringBuffer buffer = new StringBuffer();

                    for (BusLine line : info.getLines()) {
                        buffer.append("Linea: "+line.getNumber()+"\n");

                        if(!line.getName().equals("")){
                            buffer.append("Nombre: "+line.getName()+"\n");
                        }

                        buffer.append("Tmp. espera: "+line.getWaitTime()+"\n\n\n");
                    }

                    txv_businfo.setText(buffer.toString());
                    
                } catch (XmlPullParserException e) {
                    Toast.makeText(BusInfoActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(BusInfoActivity.this, "Error IO", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
