FROM gradle:jdk11 AS build
COPY --chown=gradle:gradle . /ho