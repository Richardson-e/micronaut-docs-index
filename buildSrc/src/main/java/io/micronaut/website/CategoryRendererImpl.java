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

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;

public class CategoryRendererImpl implements CategoryRenderer {
    private final String template;
    private final RepositoryRenderer repositoryRenderer;
    public CategoryRendererImpl(RepositoryRenderer repositoryRenderer) throws IOException {
        this.repositoryRenderer = repositoryRenderer;
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("category.html");
        this.template = Utils.readFromInputStream(inputStream);
    }

    @Override
    public String renderAsHtml(Category category) {
        return template.replaceAll("@title@", category.getTitle())
                .replaceAll("@image@", category.getImage())
                .replaceAll("@repositories@", category.getRepositories()
                        .stream()
                        .map(repositoryRenderer::renderAsHtml)
                        .collect(Collectors.joining("\n")));
    }
}
