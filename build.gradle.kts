plugins {
    `java-library`
    groovy
    idea
    maven
}

group = "com.adik993"
version = "1.0.0"

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
    "javadoc"(Javadoc::class) {
        if (java.sourceCompatibility >= JavaVersion.VERSION_1_10) {
            (options as CoreJavadocOptions).addBooleanOption("html5", true)
        }
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    dependsOn("classes")
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

val javadocJar by tasks.creating(Jar::class) {
    dependsOn("javadoc")
    classifier = "javadoc"
//    can be also written this way:
//    from(project.the<JavaPluginConvention>().docsDir)
    from(tasks["javadoc"])
}

artifacts.add("archives", sourcesJar)
artifacts.add("archives", javadocJar)

dependencies {
    api("io.reactivex.rxjava2:rxjava:2.2.1")
    implementation("org.jsoup:jsoup:1.11.3")
    implementation("org.apache.httpcomponents:httpclient:4.5.6")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.slf4j:slf4j-api:1.7.25")

    compileOnly("org.slf4j:slf4j-simple:1.7.25")
    compileOnly("org.projectlombok:lombok:1.18.2")

    testImplementation("org.spockframework:spock-core:1.1-groovy-2.4")
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.7.19")
    testImplementation("com.github.tomakehurst:wiremock:2.18.0")
    testImplementation("com.github.tomjankes:wiremock-groovy:0.2.0")

    annotationProcessor("org.projectlombok:lombok:1.18.2")
}

repositories {
    mavenCentral()
    jcenter()
}