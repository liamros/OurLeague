package it.our.league.app.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class AppLineChartDTO {

    private String id;
    private List<Datum> data;

    public AppLineChartDTO() {
        this.data = new ArrayList<>();
    }

    public class Datum {
        private String x;
        private Float y;

        public Datum(String x, Float y) {
            this.x = x;
            this.y = y;
        }
        public String getX() {
            return x;
        }
        public void setX(String x) {
            this.x = x;
        }
        public Float getY() {
            return y;
        }
        public void setY(Float y) {
            this.y = y;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Datum> getData() {
        return this.data;
    }

    public void addData(String x, Float y) {
        this.data.add(new Datum(x, y));
    }
}
