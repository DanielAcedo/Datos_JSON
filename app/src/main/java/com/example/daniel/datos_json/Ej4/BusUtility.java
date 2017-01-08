package com.example.daniel.datos_json.Ej4;

import android.content.Context;
import android.widget.Toast;

import com.example.daniel.datos_json.RestClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Daniel on 08/01/2017.
 */

public class BusUtility {

    public static List<BusStop> JSONToBusStops(JSONObject json) throws JSONException {
        List<BusStop> stops = new ArrayList<>();
        JSONArray array = json.getJSONArray("stops");

        for(int i = 0; i < array.length(); i++){
            BusStop stop = new BusStop();
            JSONObject stopJSON = array.getJSONObject(i);

            stop.setName(stopJSON.getString("stop_name")); //Name

            stop.setUrlInfo(stopJSON.optString("stop_url")); //URL

            stops.add(stop);
        }

        return stops;
    }

    private static String filterHTML(File file) throws IOException {
        //Filter HTML
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuffer text = new StringBuffer();
        String textLine;

        //Flags for localizing broken HTML
        boolean inScript = false;
        boolean inBrokenA = false;

        while((textLine = reader.readLine()) != null){
            //Escape special characters
            textLine = textLine.replace("&","&amp;");
            textLine = textLine.replace("?","&#63;");
            textLine = textLine.replace("<hr>", "<hr/>");

            //Filter broken non-standarized tags
            if(textLine.replace("\t","").startsWith("<script") && !textLine.replace("\t","").endsWith("</script>")){
                inScript = true;
            }

            if(textLine.replace("\t","").trim().startsWith("<a href=\"#\" data-rel=\"back\"")){
                inBrokenA = true;
            }

            if(!textLine.replace("\t","").startsWith("<h1") && !textLine.replace("\t","").startsWith("<input")
                    && !textLine.replace("\t","").startsWith("<a href=\"/emt-mobile/home.html\"") && !inScript && !inBrokenA
                    && !textLine.replace("\t","").contains("<img")){

                text.append(textLine);
            }else{
                text.append("");
            }

            if(textLine.replace("\t","").startsWith("</script>")){
                inScript = false;
            }

            if(inBrokenA && textLine.replace("\t","").startsWith("alt=\"Foto de la parada\">")){
                inBrokenA = false;
            }
        }

        reader.close();

        return text.toString();
    }

    public static BusStopInfo getInfoFromStop(File file) throws XmlPullParserException, IOException {
        final List<BusLine> busLines = new ArrayList<>();

        //Filter HTML file
        String text = filterHTML(file);

        //Parse HTML page
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(new StringReader(text.toString()));

        boolean inLine = false;
        boolean inLineNumber = false;
        boolean inLineName = false;
        boolean inTimeLeft = false;

        BusLine line = null;

        String name;

        int event = parser.next();

        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if (name.equals("li")) {
                        inLine = true;
                        line = new BusLine();
                    }

                    //Boolean checks
                    if (inLine && name.equals("span")) {

                        if (parser.getAttributeCount() != 0) {
                            if (parser.getAttributeValue(0).equals("linea")) {
                                inLineNumber = true;
                            }

                            else if (parser.getAttributeValue(0).equals("direccion")) {
                                inLineName = true;
                                line.setName(parser.nextText().trim());
                            }

                            else if (parser.getAttributeValue(0).startsWith("minutos")) {
                                inTimeLeft = true;
                                String waittime = parser.nextText().trim();

                                    line.setWaitTime(waittime);
                            }

                        }
                    }

                    //Data recovery
                    if (inLineNumber && name.equals("a")) {
                        line.setNumber(parser.nextText().trim());

                    }

                    break;

                case XmlPullParser.END_TAG:
                    name = parser.getName();

                    if (name.equals("li")) {
                        inLine = false;

                        if (line.getWaitTime() == null){
                            line.setWaitTime("No hay información");
                        }

                        busLines.add(line);
                        line = null;
                    }

                    if (inLine && name.equals("span")) {

                            if(inLineNumber){
                                inLineNumber = false;
                            }

                        if(inLineName){
                            inLineName = false;
                        }

                        if(inTimeLeft){
                            inTimeLeft = false;
                        }


                    }

                    break;
            }

            event = parser.next();
        }


        return new BusStopInfo(busLines);
    }
}
