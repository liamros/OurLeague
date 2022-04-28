package it.our.league.common.constants;

public enum RankedTierType {
    

    UNRANKED("Unranked"),
    IRON("Iron"),
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),
    PLATINUM("Platinum"),
    DIAMOND("Diamond"),
    MASTER("Master"),
    GRANDMASTER("GrandMaster"),
    CHALLENGER("Challenger"),
    IV("IV"),
    III("III"),
    II("II"),
    I("I");


    private final String description;

    private RankedTierType(String description) {
        this.description = description;
    }

    public String description() {return description;}
    


}
