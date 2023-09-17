pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "My Application"
include(":app")
include(":sport")
include(":mycity")
include(":cmdownloadlinkgenerator")
include(":racetracker")
include(":marsphoto")
include(":amphibians")
include(":bookshelf")
include(":sqlexample")
include(":inventoryapp")
include(":refreshratechanger")
include(":inventory")
include(":busschedule")
include(":datastoreexample")
