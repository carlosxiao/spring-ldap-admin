FROM insideo/jre8
VOLUME /tmp
ADD target/electricus.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
