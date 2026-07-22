plugins {
	java
	id("jacoco")
	id("org.springframework.boot") version "4.1.0"
	id("io.spring.dependency-management") version "1.1.7"
}

version = "0.0.1"
group = "br.com.fiap.security"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

tasks.named<Jar>("jar") {
	enabled = false
}

configurations.configureEach {
	exclude(group = "ch.qos.logback", module = "logback-classic")
	exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

repositories {
	mavenCentral()
}

dependencies {

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-flyway")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	runtimeOnly("org.postgresql:postgresql")
	implementation("org.flywaydb:flyway-database-postgresql")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	implementation("org.slf4j:slf4j-api")
	implementation("org.apache.logging.log4j:log4j-slf4j-impl")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.springframework.boot:spring-boot-starter-flyway-test")
	testImplementation("org.springframework.boot:spring-boot-starter-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-validation-test")

}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.named<JacocoReport>("jacocoTestReport") {
	dependsOn(tasks.test)

	reports {
		html.required.set(true)
	}

	classDirectories.setFrom(
		files(
			classDirectories.files.map {
				fileTree(it) {
					exclude(
						"**/config/**",
						"**/AuthAPIApplication.class"
					)
				}
			}
		)
	)
}