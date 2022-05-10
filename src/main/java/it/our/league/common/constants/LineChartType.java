package it.our.league.common.constants;

public enum LineChartType {
    GAMESxMIN("Games/Minute", "Game Length", "Games", ">-.0f"),
    WINRATExMIN("Winrate/Minute", "Game Length", "Winrate", ">-.0%"),
    VISIONxMIN("Vision/Minute", "Game Length", "VisionScore", ">-.0f");

    private final String name;
    private final String x;
    private final String y;
    private final String format;


    private LineChartType(String name, String x, String y, String format) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.format = format;
    }


    public String getName() {
        return name;
    }
    public String getX() {
        return x;
    }
    public String getY() {
        return y;
    }
    public String getFormat() {
        return format;
    } 

}