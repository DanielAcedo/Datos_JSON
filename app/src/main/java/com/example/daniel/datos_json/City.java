package com.example.daniel.datos_json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel on 09/01/2017.
 */

public class City implements Parcelable{
    private String name;
    private String country_code;
    private String id;

    public City(String name, String country_code, String id) {
        this.name = name;
        this.country_code = country_code;
        this.id = id;
    }

    protected City(Parcel in) {
        name = in.readString();
        country_code = in.readString();
        id = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(country_code);
        parcel.writeString(id);
    }
}
