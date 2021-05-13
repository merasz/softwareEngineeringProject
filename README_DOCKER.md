To start the application in Docker:

```
$ docker network create timeflip

$ docker run -p 3306:3306 --network timeflip --name=docker-mysql  --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_USER=spring" --env="MYSQL_PASSWORD=uraniumjazz43" --env="MYSQL_DATABASE=timeflip_guess"  mysql --lower-case-table-names=1

$ docker run -it --network timeflip -p 8080:8080 --env="MYSQL_HOST_DOCKER=docker-mysql" maxonic/webapp
```
