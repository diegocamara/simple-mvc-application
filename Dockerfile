FROM ubuntu:16.04

RUN apt-get update
RUN apt-get install apache2 openjdk-8-jdk -y
RUN apt-get clean && rm -rf /var/lib/apt/lists/*
COPY ./public-html /var/www/
COPY apache2/apache2.conf /etc/apache2
COPY apache2/sites-available /etc/apache2
COPY startup.sh /startup.sh
EXPOSE 8080 80
VOLUME ["/tmp"]
ADD simplemvc/target/simplemvc.jar simplemvc.jar
ENTRYPOINT ["/startup.sh"]