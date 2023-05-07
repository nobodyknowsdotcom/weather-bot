FROM bellsoft/liberica-openjre-alpine:17 as jre-build

ENV BOT_WEBHOOK_PATH $BOT_WEBHOOK_PATH
ENV BOT_TOKEN $BOT_TOKEN

ENV DB_USERNAME $DB_USERNAME
ENV DB_PASSWORD $DB_PASSWORD
ENV DB_HOST $DB_HOST
ENV DB_PORT $DB_PORT
ENV DB_NAME $DB_NAME

COPY build/libs/*.jar /app/app.jar
WORKDIR /app

EXPOSE 8000
ENTRYPOINT exec java -jar -Dspring.profiles.active=prod /app/app.jar