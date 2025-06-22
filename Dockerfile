FROM tomee:9-jre11

LABEL maintainer="ZTI Project"

COPY target/ZTI-1.0-SNAPSHOT.war /usr/local/tomee/webapps/

EXPOSE 8080