package com.example.daniel.datos_json;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.daniel.datos_json.Ej1.CityInfo;
import com.example.daniel.datos_json.Ej1.Ej1Activity;
import com.example.daniel.datos_json.Ej2.Ej2Activity;
import com.example.daniel.datos_json.Ej3.Ej3Activity;
import com.example.daniel.datos_json.Ej4.Ej4Activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v){
        Intent intent = null;

        switch (v.getId()){
            case R.id.btn_Ej1:
                intent = new Intent(MainActivity.this, Ej1Activity.class);
                break;
            case R.id.btn_Ej2:
                intent = new Intent(MainActivity.this, Ej2Activity.class);
                break;
            case R.id.btn_Ej3:
                intent = new Intent(MainActivity.this, Ej3Activity.class);
                break;
            case R.id.btn_Ej4:
                intent = new Intent(MainActivity.this, Ej4Activity.class);
                break;
        }

        startActivity(intent);
    }


}
