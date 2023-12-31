# backend-coding-challenge

To get the MySQL database Docker container required for dev and test, run:

```shell script
docker run -p 3306:3306 --name moviescont -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=moviesDatabase -d mysql
```

To use the provided docker-compose.yml file, first run:

```shell script
./mvnw clean package
```

WARNING: The above command will throw some errors the first time your run it, because the database is empty and there is
noting to drop, and we have `quarkus.hibernate-orm.database.generation=drop-and-create` in application.properties.

When you don't need the dev and test database anymore, use:

```shell script
docker rm -f moviescont
```

This will stop and delete the container.

Then run:

```shell script
docker build -f src/main/docker/Dockerfile.jvm -t aljazfrancic/moviesbackend:v1 .
```

And finally:

```shell script
docker compose up
```

To push (optional):

```shell script
docker push aljazfrancic/moviesbackend:v1
```

The rest of the README file was auto-generated by https://code.quarkus.io/ .

# Auto-generated text

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/backend-coding-challenge-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- JPAStreamer ([guide](https://quarkiverse.github.io/quarkiverse-docs/quarkus-jpastreamer/dev/)): Express your Hibernate
  queries as standard Java Streams
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes
  with Swagger UI
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code
  for Hibernate ORM via the active record or the repository pattern
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
