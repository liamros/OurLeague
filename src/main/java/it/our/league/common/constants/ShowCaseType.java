package it.our.league.common.constants;

public enum ShowCaseType {
    
    WORST_WR("Worst WinRate"),
    WORST_KDA("Worst KDA");


    private final String statName;

    ShowCaseType(String statName) {
        this.statName = statName;
    }

    public String statName() {return statName;}


}