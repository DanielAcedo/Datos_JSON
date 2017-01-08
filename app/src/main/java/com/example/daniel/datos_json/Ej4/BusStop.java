package com.example.daniel.datos_json.Ej4;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by daniel on 8/01/17.
 */

public class BusStop implements Parcelable{
    private String name;
    private String urlInfo;

    public static Comparator<BusStop> alphabeticallySort = new Comparator<BusStop>() {
        @Override
        public int compare(BusStop busStop, BusStop t1) {
            return busStop.getName().compareTo(t1.getName());
        }
    };

    public BusStop(){}

    public BusStop(String name, String urlInfo) {
        this.name = name;
        this.urlInfo = urlInfo;
    }

    protected BusStop(Parcel in) {
        name = in.readString();
        urlInfo = in.readString();
    }

    public static final Creator<BusStop> CREATOR = new Creator<BusStop>() {
        @Override
        public BusStop createFromParcel(Parcel in) {
            return new BusStop(in);
        }

        @Override
        public BusStop[] newArray(int size) {
            return new BusStop[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(String urlInfo) {
        this.urlInfo = urlInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(urlInfo);
    }
}
