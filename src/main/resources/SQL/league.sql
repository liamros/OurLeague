CREATE SCHEMA LEAGUE;
USE LEAGUE;

CREATE TABLE SUMMONER_INFO (
	ID INTEGER PRIMARY KEY AUTO_INCREMENT,
	GAME_NAME VARCHAR(150) NOT NULL UNIQUE KEY,
	SUMMONER_LEVEL INTEGER NOT NULL,
	SUMMONER_ICON_ID INTEGER NOT NULL,
	PUUID VARCHAR(150) NOT NULL UNIQUE KEY,
	ENCRYPTED_SUMMONER_ID VARCHAR(150) NOT NULL UNIQUE KEY,
	ACCOUNT_ID VARCHAR(150) NOT NULL UNIQUE KEY,
	UPDATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE QUEUE (
	QUEUE_TYPE_ID INTEGER PRIMARY KEY,
	QUEUE_TYPE VARCHAR(50) NOT NULL,
	UPDATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE RANK_INFO (
	SUMM_INFO_ID INTEGER,
	QUEUE_TYPE_ID INTEGER NOT NULL,
	TIER VARCHAR(20) NOT NULL,
	DIVISION VARCHAR(10) NOT NULL,
	LP INTEGER NOT NULL,
	WINS INTEGER NOT NULL,
	LOSSES INTEGER NOT NULL,
	UPDATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(SUMM_INFO_ID, QUEUE_TYPE_ID),
	FOREIGN KEY(SUMM_INFO_ID) REFERENCES SUMMONER_INFO(ID),
	FOREIGN KEY(QUEUE_TYPE_ID) REFERENCES QUEUE(QUEUE_TYPE_ID)
);

CREATE TABLE SHOWCASE_RANKING (
	ID INTEGER PRIMARY KEY AUTO_INCREMENT,
	STAT_NAME VARCHAR(150) NOT NULL,
	SUMM_INFO_ID INTEGER NOT NULL,
	POSITION INTEGER NOT NULL,
	PREV_POSITION INTEGER,
	VALUE FLOAT NOT NULL,
	DESCRIPTION VARCHAR(150) NOT NULL,
	UPDATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	FOREIGN KEY(SUMM_INFO_ID) REFERENCES summoner_info(ID)
);

INSERT INTO QUEUE VALUES(420,'RANKED_SOLO_5x5'),
						(440,'RANKED_FLEX_SR'),
						(700,'CLASH');
										
CREATE TABLE MATCH_INFO (
	MATCH_ID VARCHAR(50),
	QUEUE_TYPE_ID INTEGER,
	PATCH VARCHAR(5),
	GAME_CREATION_TIME TIMESTAMP,
	GAME_START_TIME TIMESTAMP,
	GAME_END_TIME TIMESTAMP,
	UPDATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(MATCH_ID),
	FOREIGN KEY(QUEUE_TYPE_ID) REFERENCES QUEUE(QUEUE_TYPE_ID)
);

CREATE TABLE REL_SUMMONER_MATCH (
	SUMM_INFO_ID INTEGER,
	MATCH_ID VARCHAR(50),
	CHAMPION_NAME VARCHAR(50),
	"ROLE" VARCHAR(20), 
	KILLS INTEGER,
	DEATHS INTEGER,
	ASSISTS INTEGER,
	VISION_SCORE INTEGER,
	WIN BOOLEAN,
	UPDATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(SUMM_INFO_ID, MATCH_ID),
	FOREIGN KEY(SUMM_INFO_ID) REFERENCES SUMMONER_INFO(ID),
	FOREIGN KEY(MATCH_ID) REFERENCES MATCH_INFO(MATCH_ID)
);