package it.our.league.app.controller.dto;

import java.util.List;
import java.util.Map;

public class AppShowCaseDTO {
	
	private String queueType;
	private Map<String, List<AppShowCaseRankingDTO>> showcaseRankingsByStatName;
	
	
	public String getQueueType() {
		return queueType;
	}
	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}
	public Map<String, List<AppShowCaseRankingDTO>> getShowcaseRankingsByStatName() {
		return showcaseRankingsByStatName;
	}
	public void setShowcaseRankingsByStatName(Map<String, List<AppShowCaseRankingDTO>> showcaseRankingsByStatName) {
		this.showcaseRankingsByStatName = showcaseRankingsByStatName;
	}
	
	
}
