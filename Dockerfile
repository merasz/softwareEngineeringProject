FROM ubuntu:bionic
RUN apt-get update
RUN apt-get install openjdk-11-jdk -y
RUN apt-get install maven -y
COPY src_webapp /home/webapp
EXPOSE 8080
WORKDIR /home/webapp
RUN mvn dependency:go-offline
CMD ["mvn","spring-boot:run"]
