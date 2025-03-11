#!/bin/bash
echo "Starting Tomcat service..."
sudo systemctl start tomcat

# Tomcat이 정상적으로 시작되었는지 확인
if systemctl is-active --quiet tomcat; then
    echo "Tomcat service started successfully."
    exit 0
else
    echo "ERROR: Tomcat service failed to start."
    exit 1
fi
