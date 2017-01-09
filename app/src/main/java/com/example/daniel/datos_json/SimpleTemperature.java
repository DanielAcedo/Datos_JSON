package com.example.daniel.datos_json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by usuario on 9/01/17.
 */

public class SimpleTemperature {
    @SerializedName("min")
    private float minTemp;
    @SerializedName("max")
    private float maxTemp;

    public SimpleTemperature(float minTemp, float maxTemp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }
}
