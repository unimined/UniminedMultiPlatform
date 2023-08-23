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

tasks {
    jar { enabled = false }

    register<Jar>("fabricJar") {
        from(sourceSets.getByName(fabricSourceSet).output, sourceSets.main.get().output)
        archiveClassifier.set("fabric")
    }
    register<Jar>("forgeJar") {
        from(sourceSets.getByName(forgeSourceSet).output, sourceSets.main.get().output)
        archiveClassifier.set("forge")
    }
    named<ProcessResources>("processFabricResources"){
        inputs.property("version", modVersion)
        filesMatching("fabric.mod.json"){ expand(mutableMapOf("version" to modVersion)) }
    }
    named<ProcessResources>("processForgeResources"){
        inputs.property("version", modVersion)
        filesMatching("META-INF/mods.toml"){ expand(mutableMapOf("version" to modVersion)) }
    }
}

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven") { name = "sponge" }
}

dependencies{
    configurations[mainImplementation]("org.spongepowered:mixin:0.8.5-SNAPSHOT")
}

unimined.useGlobalCache = false

unimined.minecraft(sourceSets[mainSourceSet], sourceSets[fabricSourceSet], sourceSets[forgeSourceSet]) {
    version(mcVersion)
    side("client")
    mappings {
        intermediary()
        yarn("1")
    }

    when (this.sourceSet.name) {
        mainSourceSet -> { defaultRemapJar = false }
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