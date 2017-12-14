#!/bin/bash
# apache2ctl start
sh /opt/tomcat/bin/startup.sh
exec "$@"