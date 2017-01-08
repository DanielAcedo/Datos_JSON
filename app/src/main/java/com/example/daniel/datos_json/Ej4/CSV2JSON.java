package com.example.daniel.datos_json.Ej4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by daniel on 8/01/17.
 */

/**
 * Class used for converting CSV files to JSONObjects. Files must contain a first header row.
 * @author Daniel Acedo Calderón
 */
public class CSV2JSON {
    private String content;
    private String delimiter;

    public CSV2JSON(String content, String delimiter){
        this.content = content;
        this.delimiter = delimiter;
    }

    public JSONObject convert() throws IOException, JSONException {
        String[] headers = content.substring(0, content.indexOf("\n")).split(delimiter);
        String data = content.substring(content.indexOf("\n")+2);

        JSONObject output = new JSONObject();
        JSONArray array = new JSONArray();

        BufferedReader reader = new BufferedReader(new StringReader(data));
        String line;

        while((line = reader.readLine()) != null){
            JSONObject stop = new JSONObject();
            String[] fields = line.split(delimiter);

            for(int i = 0; i < headers.length; i++){
                stop.put(headers[i], fields[i]);
            }

            array.put(stop);
        }

        reader.close();
        output.put("stops", array);

        return output;
    }

}
