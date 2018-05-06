FROM maven:3.5-jdk-8-alpine
RUN ls -l
WORKDIR /code
ADD . /code/
RUN mvn package -Dmaven.test.skip=true
EXPOSE 8081
CMD ["java", "-jar", "target/InciManager_i1a-0.0.1.jar", "--spring.kafka.bootstrap-servers=kafka:9092"]
