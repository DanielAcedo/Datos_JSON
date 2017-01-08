package com.example.daniel.datos_json.Ej4;

/**
 * Created by daniel on 8/01/17.
 */

public class BusStop {
    private String name;
    private String urlInfo;

    public BusStop(){}

    public BusStop(String name, String urlInfo) {
        this.name = name;
        this.urlInfo = urlInfo;
    }

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
}
