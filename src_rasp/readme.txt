kopieren von rasp nach lokal 

vom eigenen rechner als beispiel, pfad muss dementsprechen noch auf das eigene system angepasst werden
scp -r pi@10.0.0.9:/home/pi/src_rasp /home/michael/Schreibtisch/all_projects/abgabe/g6t1/src_ble/bleclient/

kopieren von lokal nach rasp
scp -r /home/michael/Schreibtisch/all_projects/abgabe/g6t1/src_ble/bleclient/ pi@10.0.0.9:/home/pi/src_rasp/

wie ist nun vorzugehen?
Docker mit unserer Webapp starten
Webapp unter localhost:8080 starten
mit admin und passwd einlogen
in reiter Raspberry navigieren
hierbei nun bestehenden Raspberry verwenden oder neuen anlegen

Dies erfolgt auf dem RASPBERRY.
Daten sollten wie oben gezeigt vom lokalen Rechner auf der die WebApp läuft, auf den Raspberry
übertragen werden, am Raspberry sollte nur der src_rasp ordner kopiert werden.
Es wird davon ausgegangen das der Raspberry schon soweit konfiguriert ist, das nach konfiguration
der config.json das programm läuffähig ist und keine weiteren abhängigkeiten bestehen.

In der Webapp im Reiter Raspberry findet man die informationen um das config.json richtig zu setzen:
sollte der bestehende mit dem namen TopGamerz gewählt werden sehen die configs so aus:
TopGamerz - 192.168.0.10 - 7173b055-4674-4ca2-8348-60e1b3fa8204
die IP 192.168.0.10 in das file config.json im feld ipAddress hinzufügen sollte sie nicht schon konfiguriert sein
7173b055-4674-4ca2-8348-60e1b3fa8204 im feld api key hinzufügen wenn nicht vorhanden

nun eine shell öffnen mit welcher wir herausfinden welcher hostname/ip unser raspberry besitzt.
Ein einfaches kommando dafür wäre: hostname -I
oder: ifconfig

nun die erhaltene IP im feld backend einfügen
in unserem fall war es eine Ip im lokalen netzwerk 10.0.0.11


danach ./runner.sh ausführen und der raspberry sollte nun die ble kommunikation mit dem timeflip beginnen
sollte der Timeflip nun auch richtig aufgesetzt sein, kann der REST austausch nun wirklich stattfinden.



