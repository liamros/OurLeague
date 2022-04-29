package it.our.league.app.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class AppLineChartDTO {

    private String id;
    private List<Datum> data;

    public AppLineChartDTO() {
        this.data = new ArrayList<>();
    }

    @SuppressWarnings("unused")
    private class Datum {
        private String x;
        private Integer y;

        public Datum(String x, Integer y) {
            this.x = x;
            this.y = y;
        }
        public String getX() {
            return x;
        }
        public void setX(String x) {
            this.x = x;
        }
        public Integer getY() {
            return y;
        }
        public void setY(Integer y) {
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

    public void addData(String x, Integer y) {
        this.data.add(new Datum(x, y));
    }
}
