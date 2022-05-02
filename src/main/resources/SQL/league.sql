CREATE SCHEMA League;
USE League;

CREATE TABLE summoner_info (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	game_name VARCHAR(150) NOT NULL UNIQUE KEY,
	summoner_level INTEGER NOT NULL,
	summoner_icon_id INTEGER NOT NULL,
	puuid VARCHAR(150) NOT NULL UNIQUE KEY,
	encrypted_summoner_id VARCHAR(150) NOT NULL UNIQUE KEY,
	account_id VARCHAR(150) NOT NULL UNIQUE KEY,
	insert_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE queue (
	queue_type_id INTEGER PRIMARY KEY,
	queue_type VARCHAR(50) NOT NULL,
	insert_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE rank_info (
	summ_info_id INTEGER,
	queue_type_id INTEGER NOT NULL,
	tier VARCHAR(20) NOT NULL,
	division VARCHAR(10) NOT NULL,
	lp INTEGER NOT NULL,
	wins INTEGER NOT NULL,
	losses INTEGER NOT NULL,
	insert_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(summ_info_id, queue_type_id),
	FOREIGN KEY(summ_info_id) REFERENCES summoner_info(id),
	FOREIGN KEY(queue_type_id) REFERENCES queue(queue_type_id)
);

CREATE TABLE showcase_ranking (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	stat_name VARCHAR(150) NOT NULL,
	summ_info_id INTEGER NOT NULL,
	position INTEGER NOT NULL,
	prev_position INTEGER,
	value FLOAT NOT NULL,
	description VARCHAR(150) NOT NULL,
	insert_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	FOREIGN KEY(summ_info_id) REFERENCES summoner_info(id)
);

INSERT INTO queue(queue_type_id, queue_type) VALUES	(420,'RANKED_SOLO_5x5'),
													(440,'RANKED_FLEX_SR'),
													(700,'CLASH');
										
CREATE TABLE match_info (
	match_id VARCHAR(50),
	queue_type_id INTEGER,
	patch VARCHAR(5),
	game_creation_time TIMESTAMP,
	game_start_time TIMESTAMP,
	game_end_time TIMESTAMP,
	insert_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(match_id),
	FOREIGN KEY(queue_type_id) REFERENCES queue(queue_type_id)
);

CREATE TABLE rel_summoner_match (
	summ_info_id INTEGER,
	match_id VARCHAR(50),
	champion_name VARCHAR(50),
	role VARCHAR(20), 
	kills INTEGER,
	deaths INTEGER,
	assists INTEGER,
	vision_score INTEGER,
	win BOOLEAN,
	insert_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(summ_info_id, match_id),
	FOREIGN KEY(summ_info_id) REFERENCES summoner_info(id),
	FOREIGN KEY(match_id) REFERENCES match_info(match_id)
);