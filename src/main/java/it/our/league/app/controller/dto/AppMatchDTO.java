package it.our.league.app.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class AppMatchDTO {

	@SuppressWarnings("unused")
	private class ParticipantDTO {
		private String gameName;
		private String champion;
		private Integer kills;
		private Integer deaths;
		private Integer assists;
		private boolean win;

		public ParticipantDTO(String gameName, String champion, Integer kills, Integer deaths, Integer assists, boolean win) {
			this.gameName = gameName;
			this.champion = champion;
			this.kills = kills;
			this.deaths = deaths;
			this.assists = assists;
			this.win = win;
		}

	}

	public AppMatchDTO() {
		this.blueTeam = new ArrayList<>();
		this.redTeam = new ArrayList<>();
	}

	private Long startTime;
	private Long endTime;
	private List<ParticipantDTO> blueTeam;
	private List<ParticipantDTO> redTeam;

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public List<ParticipantDTO> getBlueTeam() {
		return blueTeam;
	}

	public void setBlueTeam(List<ParticipantDTO> blueTeam) {
		this.blueTeam = blueTeam;
	}

	public void addBlueParticipant(String gameName, String champion, Integer kills, Integer deaths, Integer assists, boolean win) {
		this.blueTeam.add(new ParticipantDTO(gameName, champion, kills, deaths, assists, win));
	}

	public List<ParticipantDTO> getRedTeam() {
		return redTeam;
	}

	public void setRedTeam(List<ParticipantDTO> redTeam) {
		this.redTeam = redTeam;
	}

	public void addRedParticipant(String gameName, String champion, Integer kills, Integer deaths, Integer assists, boolean win) {
		this.redTeam.add(new ParticipantDTO(gameName, champion, kills, deaths, assists, win));
	}
}
