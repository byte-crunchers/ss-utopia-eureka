
# ss-utopia-eureka

### Eureka Server
1. start application
2. see eureka server dashboard - http://localhost:8761/

### Sample Eureka Client
1. use UtopiaSpring - https://github.com/byte-crunchers/ss-utopia-auth/tree/feature/feature_branch/UtopiaSpring
2. start application
3. see the client registered in the dashboard with a random port

### Gateway
1. start application
2. see the gateway registered in the dashboard
3. use postman to send a JWT authorized request to https://localhost:9090/utopia/test
4. get response with status code 200 OK

---

### To set up a microservice as a eureka client, add the following changes:

1. Add bean `@EnableEurekaClient` to Application.java

2. Add dependencies to pom.xml:
```
  <properties>
    <java.version>1.8</java.version>
    <spring-cloud.version>2020.0.3</spring-cloud.version>
  </properties>

  <dependencies>

    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>

  </dependencies>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>${spring-cloud.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>
```

3. Add to application.properties:
```
spring.application.name = eureka-client

eureka.instance.nonSecurePortEnabled=false
eureka.instance.securePortEnabled=true

eureka.client.enabled = false

```

Eureka client is now set up, but disabled.

To register with the eureka server on a random port, change application.properties:

```
eureka.client.enabled = true
server.port=0

```



