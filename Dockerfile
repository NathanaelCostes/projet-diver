FROM amazoncorretto:11.0.21

COPY target/cpo2-tp-jdbc-1.0-SNAPSHOT-shaded.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]