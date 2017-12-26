FROM ubuntu:16.04

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update \
    && apt-get install -y --no-install-recommends apache2 \
    libapache2-mod-jk \
    nano

VOLUME ["/var/log/apache2"]
EXPOSE 80 443

ENTRYPOINT ["apache2ctl", "-D", "FOREGROUND"]