plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	id("io.freefair.lombok") version "8.6"
	application
}

group = "simple.crud"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

application {
	mainClass = "simple.crud.todo.TodoApplication"
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.liquibase:liquibase-core")
	implementation("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.mapstruct:mapstruct:1.6.0.Beta2")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")

	implementation("org.springframework.boot:spring-boot-starter-cache")

	implementation("com.h2database:h2")

	testImplementation("org.testcontainers:testcontainers:1.17.3")
	testImplementation("org.testcontainers:postgresql:1.17.3")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

	implementation("org.hibernate.orm:hibernate-core:6.6.1.Final")

	implementation("org.springframework:spring-orm:6.1.14")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
