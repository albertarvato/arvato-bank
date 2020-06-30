FROM adoptopenjdk/openjdk8:jdk8u242-b08-alpine

COPY spring-0.0.1-SNAPSHOT.jar /home/spring-0.0.1-SNAPSHOT.jar

COPY entrypoint.sh /home/entrypoint.sh
RUN chown root:root /home/entrypoint.sh
RUN chmod +x /home/entrypoint.sh
WORKDIR /home/

ENTRYPOINT ["/home/entrypoint.sh"]

EXPOSE 8080

