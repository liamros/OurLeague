# clown.gg

***Base Information***

Maven download https://maven.apache.org/download.cgi
    Variabili d'ambiente : MAVEN_HOME=%path%\apache-maven-3.8.4
                           aggiungere nel Path %path\apache-maven-3.8.4\bin
                           
JDK 11

Scaricare wlp e aggiungere al Path %path%\wlp\bin

mvn install, questo produce un war, posizionato in C:\Users\%user%\.m2\repository\it\kekw\clowngg\0.0.1-SNAPSHOT\, questo path va aggiunto al server.xml che si trova in wlp\usr\servers\defaultServer\


server run defaultSever

---

***Data Structure Diagram***

![alt text](https://github.com/liamros/clown.gg/blob/master/SQL/diagram.png?raw=true)

---

***TODOs***

- [x] Initial data structure
- [x] Insert Summoner API
- [x] Update All Ranks API
- [ ] Initial data for FE API
- [x] ProfileIcon API with img resizing
- [x] Add default headers configuration to RestAdapter
- [ ] Evaluate possibility of a batch which periodically updates ranks on DB
- [ ] Custom DTOs for response
- [ ] Custom Exceptions and Exception handling
- [ ] Evaluate possibility of saving 1 to 10 matches per summoner on DB or Firebase
- [ ] MatchHistory API
- [x] Initialize React
- [x] Design homepage
- [x] ShowCase FE
- [ ] Animate and style ShowCase FE
- [ ] StatsSection FE
- [ ] PairStatsSection FE
- [ ] MatchHistory FE
