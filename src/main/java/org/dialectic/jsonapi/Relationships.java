package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode
public class Relationships {
    private Collection<Relationship> relationshipList;

    public Relationships(Relationship... relationship) {
        this.relationshipList = Arrays.asList(relationship);
    }

    @JsonValue
    private Map serialize() throws IOException {
        return relationshipList.stream()
                .collect(
                        Collectors.toMap(Relationship::getResourceType, r -> r)
                );
    }

    @JsonCreator
    private static Relationships deserialize(Map<String, Relationship> relationshipsMap) {
        relationshipsMap.forEach((key, value) -> value.setResourceType(key));
        Relationships relationships = new Relationships();
        relationships.relationshipList = new ArrayList<>(relationshipsMap.values());
        return relationships;
    }

    public Collection<Relationship> relationshipList() {
        return Collections.unmodifiableCollection(relationshipList);
    }
}
