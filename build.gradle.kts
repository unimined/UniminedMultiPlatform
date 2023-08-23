import xyz.wagyourtail.unimined.api.unimined

plugins {
    id("xyz.wagyourtail.unimined") version "1.0.3"
    id("java")
}

val modVersion = project.properties["mod_version"] as String
val mcVersion = project.properties["minecraft_version"] as String
val fabricVersion = project.properties["fabric_loader_version"] as String
val forgeVersion = project.properties["forge_version"] as String

val mainImplementation = "mainImplementation"
val forgeImplementation = "forgeImplementation"
val fabricImplementation = "fabricImplementation"

val mainSourceSet = "main"
val forgeSourceSet = "forge"
val fabricSourceSet = "fabric"

configurations {
    create(mainImplementation)
    create(forgeImplementation ).extendsFrom(configurations.getByName(mainImplementation))
    create(fabricImplementation).extendsFrom(configurations.getByName(mainImplementation))
}

sourceSets {
    main {
        compileClasspath += configurations[mainImplementation]
        runtimeClasspath += configurations[mainImplementation]
    }
    create(forgeSourceSet) {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
    create(fabricSourceSet) {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

tasks.register<Jar>("fabricJar") {
    from(sourceSets.getByName(fabricSourceSet).output, sourceSets.main.get().output)
    archiveClassifier.set("fabric")
}

tasks.register<Jar>("forgeJar") {
    from(sourceSets.getByName(forgeSourceSet).output, sourceSets.main.get().output)
    archiveClassifier.set("forge")
}

tasks.jar {
    enabled = false
}

tasks.getByName("processFabricResources") {
    inputs.property("version", modVersion)
}

tasks.getByName("processForgeResources") {
    inputs.property("version", modVersion)
}

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven") { name = "sponge" }
}

dependencies{
    configurations[mainImplementation]("org.spongepowered:mixin:0.8.5-SNAPSHOT")
}

unimined.useGlobalCache = false
listOf(
    sourceSets[mainSourceSet],
    sourceSets[fabricSourceSet],
    sourceSets[forgeSourceSet]
).map {
    unimined.minecraft(it) {
        version(mcVersion)
        side("client")
        mappings {
            intermediary()
            yarn("1")
        }

        when (it.name) {
            mainSourceSet -> {
                defaultRemapJar = false
            }
            fabricSourceSet -> {
                fabric{
                    loader(fabricVersion)
                }
            }
            forgeSourceSet -> {
                minecraftForge {
                    loader(forgeVersion)
                    mixinConfig("modid.mixins.json")
                }
            }
        }
    }
}