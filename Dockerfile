FROM ubuntu:16.04
RUN apt-get update && apt-get install apache2 apache2-utils openjdk-8-jdk -y && service apache2 start
EXPOSE 8080 80
VOLUME ["/tmp"]
ADD simplemvc/target/simplemvc.jar simplemvc.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/simplemvc.jar", "-D", "FOREGROUND"]
