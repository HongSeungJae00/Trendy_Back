#!/bin/bash
echo "Deploying application..."

DEPLOY_DIR="/home/ec2-user/Trendy_Back"
WAR_FILE="$DEPLOY_DIR/Trendy_Back/build/libs/Back-0.0.1-SNAPSHOT.war"

# WAR 파일이 S3에서 배포되었는지 확인
if [ -f "$WAR_FILE" ]; then
    echo "Copying new WAR file to Tomcat webapps..."
    sudo cp "$WAR_FILE" /home/ec2-user/tomcat/webapps/
    cd /home/ec2-user/tomcat/webapps/
    sudo mv Back-0.0.1-SNAPSHOT.war ROOT.war
    echo "Deployment completed."

else
    echo "ERROR: WAR file not found!"
    exit 1
fi

exit 0

