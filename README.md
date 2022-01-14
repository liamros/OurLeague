# clown.gg


Maven download https://maven.apache.org/download.cgi
    Variabili d'ambiente : MAVEN_HOME=%path%\apache-maven-3.8.4
                           aggiungere nel Path %path\apache-maven-3.8.4\bin
                           
JDK 11

Scaricare wlp e aggiungere al Path %path%\wlp\bin

mvn install, questo produce un war, posizionato in C:\Users\%user\.m2\repository\it\kekw\clowngg\0.0.1-SNAPSHOT\, questo path va aggiunto al server.xml che si trova in wlp\usr\servers\defaultServer\


server run defaultSever
