package org.dialectic.jsonapi.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.dialectic.jsonapi.*;
import org.dialectic.jsonapi.links.ResourceLinks;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.dialectic.jsonapi.Meta.ofEntry;
import static org.dialectic.jsonapi.ResourceIdentifier.of;
import static org.dialectic.jsonapi.ResourceLinkage.nonEmptyToMany;
import static org.dialectic.jsonapi.ResourceLinkage.nonEmptyToOne;
import static org.dialectic.jsonapi.links.ResourceLinks.links;
import static org.dialectic.jsonapi.links.ResourceLinks.self;
import static org.dialectic.jsonapi.testsupport.TestSupport.*;
import static java.util.Collections.singletonList;

public class ExampleTest {
    private TypeReference<MultiDataResponse<ExampleResource>> typeRef = new TypeReference<MultiDataResponse<ExampleResource>>() {
    };

    private byte[] json;

    @Before
    public void setup() throws URISyntaxException, IOException {
        URL resourceAsStream = this.getClass().getClassLoader().getResource("./example.json");

        json = Files.readAllBytes(Paths.get(resourceAsStream.toURI()));
    }

    @Test
    public void exampleJson() throws IOException, URISyntaxException {

        MultiDataResponse<ExampleResource> object = objectMapper.readValue(json, typeRef);

        String jsonString = toJsonString(object);

        assertJsonEquals(new String(json), jsonString);

        MultiDataResponse<ExampleResource> deserializedObject = objectMapper.readValue(jsonString, typeRef);

        assertJsonEquals(jsonString, toJsonString(deserializedObject));

    }


    @Test
    public void exampleObject() throws URISyntaxException, IOException {
        ExampleResource exampleResource = new ExampleResource();
        exampleResource.id = "1";
        exampleResource.type = "articles";
        exampleResource.title = "JSON API paints my bikeshed!";
        exampleResource.relationships = new Relationships(
                Relationship.create(
                        "author",
                        nonEmptyToOne("9", "people")
                ).withLinks(
                        links("http://example.com/articles/1/relationships/author", "http://example.com/articles/1/author")
                ),
                Relationship.create(
                        "comments",
                        nonEmptyToMany(
                                of("5", "comments"),
                                of("12", "comments")
                        )
                ).withLinks(
                        links("http://example.com/articles/1/relationships/comments", "http://example.com/articles/1/comments")
                )
        );
        Resource r1 = new Resource() {
            @JsonProperty("first-name")
            String firstName = "Dan";

            @JsonProperty("last-name")
            String lastName = "Gebhardt";

            @JsonProperty("twitter")
            String twitter = "dgeb";

            @Override
            public String getJsonApiDataId() {
                return "9";
            }

            @Override
            public String getJsonApiDataType() {
                return "people";
            }

            @Override
            public ResourceLinks getLinks() {
                return self("http://example.com/people/9");
            }
        };

        ResourceWithRelationships r2 = new ResourceWithRelationships() {
            @JsonProperty("body")
            String body = "First!";

            @Override
            public String getJsonApiDataId() {
                return "5";
            }

            @Override
            public Relationships getRelationships() {
                return new Relationships(Relationship.create("author", nonEmptyToOne("2", "people")));
            }

            @Override
            public String getJsonApiDataType() {
                return "comments";
            }

            @Override
            public ResourceLinks getLinks() {
                return self("http://example.com/comments/5");
            }
        };

        ResourceWithRelationships r3 = new ResourceWithRelationships() {
            @JsonProperty("body")
            String body = "I like XML better";

            @Override
            public String getJsonApiDataId() {
                return "12";
            }

            @Override
            public Relationships getRelationships() {
                return new Relationships(Relationship.create("author", nonEmptyToOne("9", "people")));
            }

            @Override
            public String getJsonApiDataType() {
                return "comments";
            }

            @Override
            public ResourceLinks getLinks() {
                return self("http://example.com/comments/12");
            }
        };

        MultiDataResponse<ExampleResource> response = JsonApiResponse.multiDataResponse(singletonList(exampleResource))
                .withLinks(self("Link"))
                .withIncludedResources(r1, r2, r3)
                .withMeta(ofEntry("metaKeyOuter", "metaValueOuter"));

        String toJson = toJsonString(response);
        assertJsonEquals(new String(json), toJson);

        MultiDataResponse<ExampleResource> deserializedResponse = objectMapper.readValue(toJson, typeRef);

        assertJsonEquals(new String(json), toJsonString(deserializedResponse));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ExampleResource implements ResourceWithRelationships {
        @JsonIgnore
        private String id;

        @JsonIgnore
        private String type;

        @JsonIgnore
        private Relationships relationships;

        @JsonProperty
        private String title;

        @Override
        public Relationships getRelationships() {
            return relationships;
        }

        @Override
        public String getJsonApiDataId() {
            return id;
        }

        @Override
        public String getJsonApiDataType() {
            return type;
        }

        @Override
        public ResourceLinks getLinks() {
            return self("http://example.com/articles/1");
        }

        @Override
        public Meta getMeta() {
            return ofEntry("metaKey", "metaValue");
        }
    }
}