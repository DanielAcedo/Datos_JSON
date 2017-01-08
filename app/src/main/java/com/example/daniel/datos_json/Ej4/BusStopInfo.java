package com.example.daniel.datos_json.Ej4;

import java.util.List;

/**
 * Created by Daniel on 08/01/2017.
 */

public class BusStopInfo {
    List<BusLine> lines;

    public BusStopInfo(List<BusLine> lines) {
        this.lines = lines;
    }

    public List<BusLine> getLines() {
        return lines;
    }

    public void setLines(List<BusLine> lines) {
        this.lines = lines;
    }
}
