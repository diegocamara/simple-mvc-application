FROM ubuntu:16.04

RUN apt-get update
RUN apt-get install apache2 libapache2-mod-jk openjdk-8-jdk tomcat8 nano -y
RUN apt-get clean && rm -rf /var/lib/apt/lists/*
COPY ./public-html /var/www/
COPY apache2/apache2.conf /etc/apache2
COPY apache2/sites-available /etc/apache2/sites-available
COPY workers.properties /etc/libapache2-mod-jk
COPY startup.sh /startup.sh
EXPOSE 80
VOLUME ["/tmp"]
ADD simplemvc/target/simplemvc.jar simplemvc.jar
ENTRYPOINT ["/startup.sh"]