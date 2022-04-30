package it.our.league.app.controller.dto;

import java.util.List;

public class AppLineChartWrapperDTO {
    
    private String xUnit;
    private String yUnit;
    private String name;
    private String format;
    private List<AppLineChartDTO> charts;
    private Integer minY;
    private Integer maxY;

    public String getxUnit() {
        return xUnit;
    }
    public void setxUnit(String xUnit) {
        this.xUnit = xUnit;
    }
    public String getyUnit() {
        return yUnit;
    }
    public void setyUnit(String yUnit) {
        this.yUnit = yUnit;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public List<AppLineChartDTO> getCharts() {
        return charts;
    }
    public void setCharts(List<AppLineChartDTO> charts) {
        this.charts = charts;
    }
    public Integer getMinY() {
        return minY;
    }
    public void setMinY(Integer minY) {
        this.minY = minY;
    }
    public Integer getMaxY() {
        return maxY;
    }
    public void setMaxY(Integer maxY) {
        this.maxY = maxY;
    }

    
}
