package com.example.daniel.datos_json.Ej1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.daniel.datos_json.City;
import com.example.daniel.datos_json.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 09/01/2017.
 */

public class CityAdapter extends ArrayAdapter<City>{

    public CityAdapter(Context context, List<City> cities){
        super(context, R.layout.layout_city, new ArrayList<City>(cities));
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        CityHolder holder = null;

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.layout_city, null);
            holder = new CityHolder();

            holder.txv_name = (TextView)v.findViewById(R.id.txv_name);
            holder.txv_countryCode = (TextView)v.findViewById(R.id.txv_countryCode);

            v.setTag(holder);
        }else{
            holder = (CityHolder)v.getTag();
        }

        holder.txv_name.setText(getItem(position).getName());
        holder.txv_countryCode.setText(getItem(position).getCountry_code());

        return v;
    }

    public static class CityHolder{
        TextView txv_name, txv_countryCode;
    }
}
