package it.our.league.common.constants;

public enum ShowCaseType {
    
    WORST_WR("Worst WinRate"),
    WORST_KDA("Worst KDA"),
    BEST_WR("Best WinRate"),
    BEST_KDA("Best KDA"),
    HIGHEST_RANK("Highest Rank"),
    HIGHEST_KILLS("Highest Kills");;


    private final String statName;

    ShowCaseType(String statName) {
        this.statName = statName;
    }

    public String statName() {return statName;}


}