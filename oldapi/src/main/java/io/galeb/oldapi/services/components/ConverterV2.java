/*
 * Copyright (c) 2014-2018 Globo.com - ATeam
 * All rights reserved.
 *
 * This source is subject to the Apache License, Version 2.0.
 * Please see the LICENSE file for more information.
 *
 * Authors: See AUTHORS file
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.galeb.oldapi.services.components;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Iterators;
import io.galeb.core.entity.AbstractEntity;
import io.galeb.oldapi.exceptions.BadRequestException;
import io.galeb.oldapi.exceptions.NotFoundException;
import io.galeb.oldapi.services.LinkProcessor;
import io.galeb.oldapi.services.http.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ConverterV2 implements LinkProcessor {

    private static final Logger LOGGER = LogManager.getLogger(ConverterV2.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${api.url}")
    private String apiUrl;

    public ConverterV2() {
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private class RawJsonHalData {
        private List<JsonNode> nodes;
        private JsonNode links;
        private JsonNode page;
        private int status;
        private String error;

        RawJsonHalData() {
            this(Collections.emptyList());
        }

        RawJsonHalData(List<JsonNode> nodes) {
            this.nodes = nodes;
        }

        List<JsonNode> getNodes() {
            return nodes;
        }

        JsonNode getLinks() {
            return links;
        }

        RawJsonHalData setLinks(JsonNode links) {
            this.links = links;
            return this;
        }

        JsonNode getPage() {
            return page;
        }

        RawJsonHalData setPage(JsonNode page) {
            this.page = page;
            return this;
        }

        int getStatus() {
            return status;
        }

        RawJsonHalData setStatus(int status) {
            this.status = status;
            return this;
        }

        String getError() {
            return error;
        }

        RawJsonHalData setError(String error) {
            this.error = error;
            return this;
        }
    }

    public class V2JsonHalData {
        private List<Resource<? extends AbstractEntity>> v2entities = new ArrayList<>();
        private Map<String, String> links = new HashMap<>();
        private Map<String, String> metadata = new HashMap<>();

        public List<Resource<? extends AbstractEntity>> getV2entities() {
            return v2entities;
        }

        public V2JsonHalData setV2entities(List<Resource<? extends AbstractEntity>> v2entities) {
            this.v2entities = v2entities;
            return this;
        }

        public Map<String, String> getLinks() {
            return links;
        }

        public V2JsonHalData setLinks(Map<String, String> links) {
            this.links.putAll(links);
            return this;
        }

        public Map<String, String> getMetadata() {
            return metadata;
        }

        public V2JsonHalData setMetadata(Map<String, String> metadata) {
            this.metadata.putAll(metadata);
            return this;
        }
    }

    private RawJsonHalData toRawJsonHal(Response response) {
        JsonNode json;
        int responseStatus = response.hasResponseStatus() ? response.getStatusCode() : -1;
        try {
            json = mapper.readTree(response.getResponseBody());
        } catch (IOException e) {
            return new RawJsonHalData().setStatus(responseStatus).setError(e.getMessage());
        }
        if (responseStatus < 100 || responseStatus > 399) {
            String error = response.getResponseBody();
            return new RawJsonHalData().setStatus(responseStatus).setError(error);
        }
        JsonNode links = null;
        JsonNode page = null;
        List<JsonNode> nodes = new ArrayList<>();
        if (json.has("links")) links = json.get("links");
        if (json.has("_links")) links = json.get("_links");
        if (json.has("page")) page = json.get("page");
        if (json.has("_embedded")) json = json.get("_embedded").fields().next().getValue();
        if (json.isArray()) {
            nodes.addAll(Arrays.asList(Iterators.toArray(json.elements(), JsonNode.class)));
        } else {
            nodes.add(json);
        }
        return new RawJsonHalData(nodes).setLinks(links).setPage(page).setStatus(responseStatus);
    }

    public V2JsonHalData toV2JsonHal(Response response, Class<? extends AbstractEntity> v2Class) {
        RawJsonHalData rawJsonHalData = toRawJsonHal(response);
        String error = rawJsonHalData == null ? "RAW JSON IS NULL" : rawJsonHalData.getError();
        if (error != null) {
            LOGGER.error(error);
            if (error.toLowerCase().contains("no content to map")) {
                throw new NotFoundException();
            }
            throw new BadRequestException(error);
        }
        V2JsonHalData v2JsonHal = new V2JsonHalData();
        Map<String, String> links = new HashMap<>();
        Map<String, String> metadata = new HashMap<>();
        List<Resource<? extends AbstractEntity>> v2entityCollection = new ArrayList<>();
        final JsonNode page;
        if ((page = rawJsonHalData.getPage()) != null) {
            metadata.put("size", String.valueOf(page.has("size") ? page.get("size").asInt() : 0));
            metadata.put("total_elements", String.valueOf(page.has("total_elements") ? page.get("total_elements").asInt() :0));
            metadata.put("total_pages", String.valueOf(page.has("total_pages") ? page.get("total_pages").asInt() : 0));
            metadata.put("number", String.valueOf(page.has("number") ? page.get("number").asInt() : 0));
        }
        final JsonNode rawLinks;
        if ((rawLinks = rawJsonHalData.getLinks()) != null) {
            rawLinks.fieldNames().forEachRemaining(f -> links.put(f, rawLinks.get(f).get("href").asText()));
        }
        final List<JsonNode> nodes;
        if (!(nodes = rawJsonHalData.getNodes()).isEmpty()) {
            v2entityCollection = nodes.stream().map(n -> {
                try {
                    AbstractEntity v2 = mapper.readValue(n.toString(), v2Class);
                    List<Link> entityLinks = new ArrayList<>();
                    JsonNode v2links = n.get("_links");
                    if (v2links != null) {
                        v2links.fields().forEachRemaining(l -> entityLinks.add(new Link(l.getValue().get("href").asText(), l.getKey())));
                    }
                    return new Resource<>(v2, entityLinks);
                } catch (IOException e) {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        return v2JsonHal.setLinks(links).setMetadata(metadata).setV2entities(v2entityCollection);
    }

    public Set<Link> extractLinks(V2JsonHalData v2JsonHalData, String resourceName) {
        return v2JsonHalData.getLinks().entrySet().stream()
                .map(e -> new Link(e.getValue().replaceAll(".*/" + resourceName, "/" + resourceName), e.getKey()))
                .collect(Collectors.toSet());
    }

    public AbstractEntity convertJsonStringToV2(String jsonStr, Class<? extends AbstractEntity> v2Class) {
        try {
            return mapper.readValue(jsonStr, v2Class);
        } catch (IOException e) {
            LOGGER.error(e);
            return null;
        }
    }
}