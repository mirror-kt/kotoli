FROM adoptopenjdk/openjdk11:alpine-slim as builder
RUN apk update \
    && apk upgrade \
    && apk --no-cache add tar \
    && mkdir -p /workdir /workdir/src /workdir/gradle/wrapper
COPY gradlew build.gradle.kts settings.gradle.kts gradle.properties /workdir/
COPY src /workdir/src/
COPY gradle/wrapper/ /workdir/gradle/wrapper
WORKDIR /workdir
RUN ./gradlew --no-daemon distTar && tar -xvf build/distributions/kotoli.tar

FROM adoptopenjdk/openjdk11:alpine-slim
RUN mkdir /workdir
COPY --from=builder /workdir/kotoli/ /workdir/
ENTRYPOINT /workdir/bin/kotoli
