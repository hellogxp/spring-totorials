## Documentation

### package
```shell
 ./mvnw -s /path/.m2/settings.xml clean package
```

### execution    
```shell
java -jar target/publisher-confirms-0.0.1-SNAPSHOT.jar --spring.profiles.active=publisher-confirms,receiver
```

```shell
java -jar target/publisher-confirms-0.0.1-SNAPSHOT.jar --spring.profiles.active=publisher-confirms,sender
```