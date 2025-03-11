#!/bin/bash
echo "Cleaning up previous deployment files..."

export PATH=$PATH:/usr/local/bin
source ~/.bashrc
rm -rf /home/ec2-user/Trendy_Back
rm -rf /home/ec2-user/tomcat/webapps/ROOT*
sudo chmod -R 755 /opt/codedeploy-agent/deployment-root/*
sudo chmod -R 755 /home/ec2-user/Trendy_Back

echo "Cleanup complete."
exit 0

