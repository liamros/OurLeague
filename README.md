# OurLeague


OurLeague is a webpage finalized to present data of League of Legends fetched from Riot public APIs. 
It is limited to a small circle of players, thus "OurLeague".

It's main features, for now, are celebrating the players with selected best stats, 
and to offer a set of graphs rappresenting different data related to game player performance
over time.

In the future, there will be more graphs, stats between players, match history and more!


Riot public APIs can be found [here](https://developer.riotgames.com/apis)



## System Design

Java web application which runs on "some" server. (not deployed yet, tbd)

The web app relise on a MySQL database, where data fetched from Riot APIs is stored.
This data includes Account information such as different types of IDs, ign, profile icon etc.,
basic match information and support tables for presentation.
This DB is necessary because the access to Riot public APIs is limited.

Thus, for read operations the web app will only utilize data from its DB, and this may result in it being stale,
so to make sure everything is up to date, the user must call the offered update API.

The decision of a manual update process is always result of the limited access to Riot's public APIs.

The bulk of the match data is saved on a Non-relational DB, due to the massive amount of information a single
match holds, and the number of them.
On the MySQL DB are saved the IDs to reference such matches, and the relations with the players.

Frontend is in React.

It utilizes motion.div library to animate the web page's features, and all the presented data is fetched from
the web application.
No cache is implemted yet.



## Data Structure Diagram

![alt text](https://github.com/liamros/OurLeague.gg/blob/master/src/main/resources/SQL/diagram.png?raw=true)


## TODOs
***Backend***
- [x] Initial data structure
- [x] Insert Summoner API
- [x] Update All Ranks API
- [x] Showcase data for FE API
- [ ] Change Showcase Ranking position only on updated position
- [x] ProfileIcon API with img resizing
- [x] Add default headers configuration to RestAdapter
- [x] Evaluate possibility of a batch which periodically updates ranks on DB
- [x] Custom DTOs for response
- [ ] Custom Exceptions and Exception handling
- [x] Evaluate possibility of saving 1 to 10 matches per summoner on DB or Firebase
- [x] Design and implement an asyncrounous system which finds and saves the last matches on DB and MongoDB
- [x] UpdateMatches API
- [x] Chart APIs
- [ ] Batch update matches
- [ ] MatchHistory API

***Frontend***
- [x] Initialize React
- [x] Design homepage
- [x] ShowCase
- [x] Showcase Rankings
- [x] Animate and style ShowCase
- [x] Charts
- [ ] Update button
- [ ] StatsSection
- [ ] PairStatsSection
- [ ] MatchHistory
- [ ] Style background
