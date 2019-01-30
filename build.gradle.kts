import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.gradle.build-scan") version "2.1"
    `java-library`
    groovy
    idea
    maven
    jacoco
    id("com.github.ben-manes.versions").version("0.20.0")
}

group = "com.adik993"
version = "1.0.0"

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    withType<Javadoc> {
        if (java.sourceCompatibility >= JavaVersion.VERSION_1_10) {
            (options as CoreJavadocOptions).addBooleanOption("html5", true)
        }
    }

    withType<DependencyUpdatesTask> {
        resolutionStrategy {
            componentSelection {
                all {
                    val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview")
                            .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-].*") }
                            .any { it.matches(candidate.version) }
                    if (rejected) {
                        reject("Release candidate")
                    }
                }
            }
        }
    }

    withType<JacocoReport> {
        reports {
            xml.isEnabled = true
            html.isEnabled = false
        }
        setDependsOn(withType<Test>())
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    dependsOn("classes")
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

val javadocJar by tasks.creating(Jar::class) {
    dependsOn("javadoc")
    archiveClassifier.set("javadoc")
//    can be also written this way:
//    from(project.the<JavaPluginConvention>().docsDir)
    from(tasks["javadoc"])
}

artifacts {
    archives(sourcesJar)
    archives(javadocJar)
}

dependencies {
    api("io.reactivex.rxjava2:rxjava:2.2.6")
    implementation("org.jsoup:jsoup:1.11.3")
    implementation("org.apache.httpcomponents:httpclient:4.5.7")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("org.slf4j:jcl-over-slf4j:1.7.25")

//    compileOnly("org.slf4j:slf4j-simple:1.7.25")
    compileOnly("org.projectlombok:lombok:1.18.4")

    testImplementation("org.spockframework:spock-core:1.2-groovy-2.5")
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.23.4")
    testImplementation("com.github.tomakehurst:wiremock:2.20.0")
    testImplementation("com.github.tomjankes:wiremock-groovy:0.2.0")
    testImplementation("org.apache.logging.log4j:log4j-slf4j-impl:2.11.1")


    annotationProcessor("org.projectlombok:lombok:1.18.4")
}

repositories {
    mavenCentral()
    jcenter()
}