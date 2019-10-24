load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "2.8"

RULES_JVM_EXTERNAL_SHA = "79c9850690d7614ecdb72d68394f994fef7534b292c4867ce5e7dec0aa7bdfad"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install", "artifact")
load("@rules_jvm_external//:specs.bzl", "maven")

maven_install(
    artifacts = [
        "com.github.jnr:jnr-posix:3.0.38",
        "com.fasterxml.jackson.core:jackson-annotations:2.8.10",
        "com.fasterxml.jackson.core:jackson-databind:2.8.10",
        "javax.persistence:javax.persistence-api:2.2",
        "javax.activation:activation:1.1.1",
        "javax.jms:javax.jms-api:2.0.1",
        "commons-logging:commons-logging:1.2",
        "commons-io:commons-io:2.5",
        "org.apache.activemq:artemis-commons:1.5.5",
        "org.apache.activemq:artemis-core-client:1.5.5",
        "org.apache.activemq:artemis-jms-client:1.5.5",
        "org.apache.tomcat:tomcat-annotations-api:9.0.17",
        "org.springframework.boot:spring-boot-starter-parent:1.5.9.RELEASE",
        "org.springframework.boot:spring-boot-autoconfigure:1.5.9.RELEASE",
        maven.artifact(
            group = "org.springframework.boot",
            artifact = "spring-boot-starter-data-jpa",
            version = "1.5.9.RELEASE",
            exclusions = [
                "ch.qos.logback:logback-classic",
            ]
        ),
        maven.artifact(
            group = "org.springframework.boot",
            artifact = "spring-boot-starter-test",
            version = "1.5.9.RELEASE",
            exclusions = [
                "ch.qos.logback:logback-classic",
            ]
        ),
        "org.springframework.boot:spring-boot:1.5.9.RELEASE",
        maven.artifact(
            group = "org.springframework.boot",
            artifact = "spring-boot-starter-artemis",
            version = "1.5.9.RELEASE",
            exclusions = [
                "ch.qos.logback:logback-classic",
            ]
        ),
        maven.artifact(
            group = "org.springframework.boot",
            artifact = "spring-boot-starter-data-rest",
            version = "1.5.9.RELEASE",
            exclusions = [
                "ch.qos.logback:logback-classic",
            ]
        ),
        maven.artifact(
            group = "org.springframework.boot",
            artifact = "spring-boot-starter",
            version = "1.5.9.RELEASE",
            exclusions = [
                "ch.qos.logback:logback-classic",
            ]
        ),
        "org.springframework.boot:spring-boot-test:1.5.9.RELEASE",
        "org.springframework.boot:spring-boot-test-autoconfigure:1.5.9.RELEASE",
        "org.springframework.data:spring-data-commons:1.13.9.RELEASE",
        "org.springframework.data:spring-data-jpa:1.11.9.RELEASE",
        "org.springframework.data:spring-data-rest-core:2.6.9.RELEASE",
        "org.springframework.data:spring-data-rest-webmvc:2.6.9.RELEASE",
        "org.springframework.data:spring-data-redis:1.8.9.RELEASE",
        "org.springframework.ldap:spring-ldap-core:2.3.2.RELEASE",
        "org.springframework.hateoas:spring-hateoas:0.23.0.RELEASE",
        "org.hibernate:hibernate-core:5.0.12.Final",
        "org.springframework:spring-core:4.3.13.RELEASE",
        "org.springframework:spring-orm:4.3.13.RELEASE",
        "org.springframework:spring-web:4.3.13.RELEASE",
        "org.springframework:spring-context:4.3.13.RELEASE",
        "org.springframework:spring-tx:4.3.13.RELEASE",
        "org.springframework:spring-beans:4.3.13.RELEASE",
        "org.springframework:spring-test:4.3.13.RELEASE",
        "org.springframework:spring-jdbc:4.3.13.RELEASE",
        "org.springframework:spring-jms:4.3.7.RELEASE",
        "org.springframework.security:spring-security-core:4.2.3.RELEASE",
        "org.springframework.security:spring-security-config:3.2.10.RELEASE",
        "org.springframework.security:spring-security-web:3.2.10.RELEASE",
        "org.springframework.security.oauth:spring-security-oauth2:2.0.14.RELEASE",
        "com.google.code.gson:gson:2.8.0",
        "com.google.guava:guava:23.1-jre",
        "com.h2database:h2:1.4.196",
        "org.javassist:javassist:3.22.0-GA",
        "org.apache.commons:commons-lang3:3.6",
        "org.apache.logging.log4j:log4j-api:2.8.1",
        "org.apache.logging.log4j:log4j-core:2.8.1",
        "org.apache.logging.log4j:log4j-jcl:2.8.1",
        "org.apache.logging.log4j:log4j-slf4j-impl:2.8.1",
        "org.slf4j:slf4j-api:1.7.25",
        "mysql:mysql-connector-java:6.0.6",
        "biz.paluch.redis:lettuce:4.4.2.Final",
        "com.zaxxer:HikariCP:2.7.4",
        "javax.xml.bind:jaxb-api:2.2.11",
        "com.sun.xml.bind:jaxb-core:2.2.11",
        "com.sun.xml.bind:jaxb-impl:2.2.11",
        "com.lmax:disruptor:3.3.6",
        "org.jodd:jodd-core:3.8.1",
        "io.undertow:undertow-core:2.0.22.Final",
        "io.undertow:undertow-servlet:2.0.22.Final",
        "io.dropwizard.metrics:metrics-core:3.2.2",
        "org.asynchttpclient:async-http-client:2.6.0",
        "com.timgroup:java-statsd-client:3.1.0",
        "org.jboss.xnio:xnio-api:3.3.8.Final",
        "io.netty:netty-codec-http:4.1.9.Final",
        "org.zalando:logbook-spring-boot-starter:1.8.1",
        "org.zalando:logbook-api:1.8.1",
        "org.zalando:logbook-core:1.8.1",
        "com.google.code.findbugs:jsr305:1.3.9",
        # Tests
        "org.mockito:mockito-all:1.10.19",
        "info.cukes:cucumber-core:1.2.4",
        "info.cukes:cucumber-java:1.2.4",
        "info.cukes:cucumber-junit:1.2.4",
        "info.cukes:cucumber-spring:1.2.4",
        "info.cukes:gherkin:2.12.2",
        "org.hamcrest:hamcrest-core:1.3",
        "org.hamcrest:hamcrest-library:1.3",
        "junit:junit:4.12",
        "junit:junit-dep:4.10",
        "org.assertj:assertj-core:2.6.0",
        "org.apache.commons:commons-math3:3.6.1",
        "com.jayway.restassured:rest-assured:2.9.0",
        "org.flywaydb:flyway-core:5.0.2",
        "com.github.stefanbirkner:system-rules:1.18.0",
        "com.squareup.okhttp3:mockwebserver:3.14.1",
        "org.mock-server:mockserver-core:4.0.0",
        "org.mock-server:mockserver-netty:4.0.0",
        "com.jayway.jsonpath:json-path:2.2.0",
        "net.minidev:json-smart:2.2.1",
    ],
    repositories = [
        "https://jcenter.bintray.com/",
        "https://repo1.maven.org/maven2",
        "https://repo2.maven.org/maven2",
    ],
)
