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
package io.micronaut.website.docsindex;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class IndexRendererImpl implements IndexRenderer {
    private final String template;
    private final CategoryRenderer categoryRenderer;
    private final CategoryFetcher categoryFetcher;
    private final VersionsFetcher versionsFetcher;
    private final DocVersionRenderer docVersionRenderer;
    private final ApiVersionRenderer apiVersionRenderer;

    public IndexRendererImpl(CategoryRenderer categoryRenderer,
                             CategoryFetcher categoryFetcher,
                             VersionsFetcher versionsFetcher,
                             DocVersionRenderer docVersionRenderer,
    ApiVersionRenderer apiVersionRenderer) throws IOException {
        this.categoryRenderer = categoryRenderer;
        this.template = Utils.readFromURL(this.getClass()
                .getClassLoader()
                .getResource("index.html"));
        this.categoryFetcher = categoryFetcher;
        this.versionsFetcher = versionsFetcher;
        this.docVersionRenderer = docVersionRenderer;
        this.apiVersionRenderer = apiVersionRenderer;
    }

    @Override
    public String renderAsHtml() {
        List<String> versions = versionsFetcher.versions();
        String latestVersion = versions.get(0);
        List<String> oldVersions = versions.subList(1, versions.size());
        return template.replaceAll("@version@", versions.get(0))
                .replaceAll("@doc-options@", oldVersions.stream()
                        .map(version -> docVersionRenderer.renderAsHtml(version)).collect(Collectors.joining("\n")))
                .replaceAll("@api-options@", oldVersions.stream()
                        .map(version -> apiVersionRenderer.renderAsHtml(version)).collect(Collectors.joining("\n")))
                .replaceAll("@analytics@", categoryRenderer.renderAsHtml(categoryFetcher.fetch(Type.ANALYTICS).orElseThrow()))
                .replaceAll("@api@", categoryRenderer.renderAsHtml(categoryFetcher.fetch(Type.API).orElseThrow()))
                .replaceAll("@build@", categoryRenderer.renderAsHtml(categoryFetcher.fetch(Type.BUILD).orElseThrow()))
                .replaceAll("@cloud@", categoryRenderer.renderAsHtml(categoryFetcher.fetch(Type.CLOUD).orElseThrow()))
                .replaceAll("@data-access@", categoryRenderer.renderAsHtml(categoryFetcher.fetch(Type.DATA_ACCESS).orElseThrow()))
                .replaceAll("@database-migration@", categoryRenderer.renderAsHtml(categoryFetcher.fetch(Type.DATABASE_MIGRATION).orElseThrow()))
                .replaceAll("@languages@", categoryRenderer.renderAsHtml(categoryFetcher.fetch(Type.LANGUAGES).orElseThrow()))
                .replaceAll("@messaging@", categoryRenderer.renderAsHtml(categoryFetcher.fetch(Type.MESSAGING).orElseThrow()))
                .replaceAll("@misc@", categoryRenderer.renderAsHtml(categoryFetcher.fetch(Type.MISC).orElseThrow()))
                .replaceAll("@reactive@", categoryRenderer.renderAsHtml(categoryFetcher.fetch(Type.REACTIVE).orElseThrow()))
                .replaceAll("@views@", categoryRenderer.renderAsHtml(categoryFetcher.fetch(Type.VIEWS).orElseThrow()))
                .replaceAll("@dev-and-test@", categoryRenderer.renderAsHtml(categoryFetcher.fetch(Type.DEV_AND_TEST).orElseThrow()));

    }
}
