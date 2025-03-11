#!/bin/bash
echo "Validating application health..."

APP_URL="http://localhost:8080/health"
RETRY_COUNT=3
SLEEP_INTERVAL=10

for i in $(seq 1 $RETRY_COUNT); do
    HTTP_STATUS=$(curl -o /dev/null -s -w "%{http_code}" $APP_URL)
    if [ "$HTTP_STATUS" -eq 200 ]; then
        echo "Application is healthy."
        exit 0
    else
        echo "Attempt $i: Application returned HTTP status $HTTP_STATUS. Retrying in $SLEEP_INTERVAL seconds..."
        sleep $SLEEP_INTERVAL
    fi
done

echo "ERROR: Application did not return HTTP 200 after $RETRY_COUNT attempts."
exit 1
