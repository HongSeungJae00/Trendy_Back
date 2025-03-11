#!/bin/bash
echo "Checking Tomcat health..."

if systemctl is-active --quiet tomcat; then
    echo "Tomcat service is running."
    exit 0
else
    echo "ERROR: Tomcat service is not running."
    exit 1
fi
