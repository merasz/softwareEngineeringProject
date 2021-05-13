kopieren von rasp nach lokal 

scp -r pi@10.0.0.9:/home/pi/src_rasp /home/michael/Schreibtisch/all_projects/abgabe/g6t1/src_ble/bleclient/

kopieren von lokal nach rasp
scp -r /home/michael/Schreibtisch/all_projects/abgabe/g6t1/src_ble/bleclient/ pi@10.0.0.9:/home/pi/src_rasp/


zuerst hostname setzen mit ./setHostname

danach ./runner


