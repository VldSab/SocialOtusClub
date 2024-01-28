FROM gradle:8.5 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/SocialOtusClub-0.0.1-SNAPSHOT.jar /app
COPY --from=builder /app/src/main/resources/data/people.csv /app/src/main/resources/data/people.csv

ENTRYPOINT ["java", "-jar", "SocialOtusClub-0.0.1-SNAPSHOT.jar"]