
package it.kekw.clowngg.riot.dto;

import java.util.List;

public class Perks {

    private StatPerks statPerks;
    private List<Style> styles = null;

    public StatPerks getStatPerks() {
        return statPerks;
    }

    public void setStatPerks(StatPerks statPerks) {
        this.statPerks = statPerks;
    }

    public List<Style> getStyles() {
        return styles;
    }

    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }

}
