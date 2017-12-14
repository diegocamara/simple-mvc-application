FROM ubuntu:16.04

RUN apt-get update
RUN apt-get install apache2 libapache2-mod-jk openjdk-8-jdk nano wget -y

# Tomcat Version
ENV TOMCAT_VERSION_MAJOR 8
ENV TOMCAT_VERSION_FULL  8.5.24

# Define commonly used JAVA_HOME variable
# ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/jre

RUN wget --quiet --no-cookies http://mirror.nbtelecom.com.br/apache/tomcat/tomcat-8/v8.5.24/bin/apache-tomcat-8.5.24.tar.gz -O /tmp/tomcat.tgz && \
tar xzvf /tmp/tomcat.tgz -C /opt && \
mv /opt/apache-tomcat-8.5.24 /opt/tomcat && \
rm /tmp/tomcat.tgz && \
rm -rf /opt/tomcat/webapps/examples && \
rm -rf /opt/tomcat/webapps/docs && \
rm -rf /opt/tomcat/webapps/ROOT

# Add admin/admin user
# ADD tomcat/tomcat-users.xml /opt/tomcat/conf/

# ENV CATALINA_HOME /opt/tomcat
# ENV PATH $PATH:$CATALINA_HOME/bin

# RUN apt-get clean && rm -rf /var/lib/apt/lists/*
# COPY ./public-html /var/www/
# COPY apache2/apache2.conf /etc/apache2
# COPY apache2/sites-available /etc/apache2/sites-available
# COPY workers.properties /etc/libapache2-mod-jk
# COPY startup.sh /startup.sh
# EXPOSE 80
EXPOSE 8080
# VOLUME ["/tmp"]
# ADD simplemvc/target/simplemvc.war /opt/tomcat/webapps/simplemvc.war
# ENTRYPOINT ["/startup.sh"]