/*
 * Copyright 2017-2022 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.website;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

public abstract class MicronautWebsiteDocsIndexPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getPluginManager().apply(BasePlugin.class);
        TaskContainer tasks = project.getTasks();
        DirectoryProperty buildDirectory = project.getLayout().getBuildDirectory();
        Directory projectDirectory = project.getLayout().getProjectDirectory();
        TaskProvider<RenderMicronautWebsiteDocsIndexTask> renderDocsIndex = tasks.register("renderDocsIndex", RenderMicronautWebsiteDocsIndexTask.class, task -> {
            task.getModules().convention(projectDirectory.file("modules.yml"));
            task.getReleases().convention(projectDirectory.file("releases.yml"));
            task.getDestinationFile().convention(buildDirectory.map(dir -> dir.file("index.html")));
        });
    }
}
