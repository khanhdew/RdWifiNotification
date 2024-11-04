FROM openjdk:17
LABEL authors="khanhdew"
VOLUME /tmp
EXPOSE 8080
COPY target/rd_wifi_noti-0.0.1-SNAPSHOT.jar noti-node.jar
ENTRYPOINT ["java","-jar","/noti-node.jar"]