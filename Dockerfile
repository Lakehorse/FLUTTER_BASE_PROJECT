FROM gradle:jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM gcr.io/distroless/java
COPY --from=build /home/gradle/src/build /usr/app
