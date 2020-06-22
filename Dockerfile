FROM gradle:jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM gcr.io/distroless/java
COPY --from=build /home/gradle/src/build /usr/app
EXPOSE 9085
EXPOSE 9084
EXPOSE 8025
WORKDIR /usr/app
ENTRYPOINT ["java","-cp", "./libs/*", "com.theagilemonkeys.notes.NotesAppKt", "./resources/main/sidechain-settings.conf"]