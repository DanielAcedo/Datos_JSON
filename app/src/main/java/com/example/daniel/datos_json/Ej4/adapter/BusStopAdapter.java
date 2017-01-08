package com.example.daniel.datos_json.Ej4.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.daniel.datos_json.Ej4.BusStop;
import com.example.daniel.datos_json.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 08/01/2017.
 */

public class BusStopAdapter extends ArrayAdapter<BusStop> {

    public BusStopAdapter(List<BusStop> stops, Context context){
        super(context, R.layout.layout_busstop, new ArrayList<BusStop>(stops));
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        BusStopHolder holder = null;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.layout_busstop, null);
            holder = new BusStopHolder();

            holder.txv_name = (TextView) v.findViewById(R.id.busstop_name);

            v.setTag(holder);
        }
        else{
            holder = (BusStopHolder)v.getTag();
        }

        holder.txv_name.setText(getItem(position).getName());

        return v;
    }

    public static class BusStopHolder{
        TextView txv_name;
    }
}
