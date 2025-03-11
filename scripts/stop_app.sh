#!/bin/bash
echo "Stopping Tomcat service..."

# Tomcat 서비스 중지
if systemctl is-active --quiet tomcat; then
    sudo systemctl stop tomcat
    echo "Tomcat service stopped."
else
    echo "Tomcat is not running."
fi

exit 0
