package cc.suitalk.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 *
 * @Author AlbieLiang
 *
 */
class ArbitraryGenPlugin implements Plugin<Project> {

    Project project;

    ArbitraryGenPluginExtension extension;

    ArbitraryGenTask arbitraryGenTask;

    @Override
    void apply(Project project) {
        println("project ${project.name} apply ArbitraryGenPlugin")
        this.project = project
        this.extension = project.extensions.create("arbitraryGen", ArbitraryGenPluginExtension)

        this.arbitraryGenTask = this.project.tasks.create("arbitraryGen", ArbitraryGenTask)

        this.project.tasks.whenTaskAdded { task ->
            println("when task(${task.name}) added in project(${project.name}).")
            if (task.name.startsWith('generate') && task.name.endsWith('Sources') || task.name.equals("compileJava")) {
                println("add task(${task.name}) project : ${this.project}, name : $name.")
                task.dependsOn arbitraryGenTask
            }
        }

        Task task = this.project.tasks.findByName("compileJava")
        if (task != null) {
            task.dependsOn arbitraryGenTask
        }
        project.afterEvaluate {
            println "arbitrarygen(${this.project.name}) libsDir : '${this.extension.libsDir}'"
            println "arbitrarygen(${this.project.name}) inputDir : '${this.extension.inputDir}'"
            println "arbitrarygen(${this.project.name}) outputDir : '${this.extension.outputDir}'"

            arbitraryGenTask.inputDir = this.project.file(this.extension.inputDir)
            arbitraryGenTask.outputDir = this.project.file(this.extension.outputDir)
            arbitraryGenTask.libsDir = this.project.file(this.extension.libsDir)

            arbitraryGenTask.extension = this.extension

            if (arbitraryGenTask.inputDir == null || !arbitraryGenTask.inputDir.exists()) {
                println("project: ${this.project} do not exists arbitrarygen dir.")
                return
            }
        }
    }
}

