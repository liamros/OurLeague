package it.kekw.clowngg.common.constants;

public enum RankedQueueType {
    
    RANKED_SOLO_5x5(1),
    RANKED_FLEX_SR(2);

    private final Integer id;

    RankedQueueType(Integer id) {
        this.id = id;
    }

    public Integer id() {return id;}


}
