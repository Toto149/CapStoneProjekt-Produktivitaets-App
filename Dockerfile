FROM --platform=linux/amd64 openjdk:21
LABEL maintainer="thorsten.thomann@googlemail.com"
EXPOSE 8080
ADD backend/target/productivity-app.jar productivity-app.jar
CMD ["sh", "-c", "java -jar /productivity-app.jar"]
