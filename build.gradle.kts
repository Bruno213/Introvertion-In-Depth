// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath(BuildScript.toolsGradlePlugin)
        classpath(BuildScript.jetbrainsGradlePlugin)
    }
}

plugins {
    id(BuildScript.androidApplicationId) version "7.3.1" apply false
    id(BuildScript.libraryPlugin) version "7.3.1" apply false
    id(BuildScript.jetbrainsKotlinPlugin) version "1.7.20" apply false
}

tasks.register("clean").configure {
    delete(rootProject.buildDir)
}