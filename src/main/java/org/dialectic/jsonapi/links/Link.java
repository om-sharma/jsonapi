package org.dialectic.jsonapi.links;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.dialectic.jsonapi.Meta;
import lombok.EqualsAndHashCode;

public interface Link {

    @EqualsAndHashCode
    class StringLink implements Link {
        private final String value;

        public StringLink(String value) {
            this.value = value;
        }

        @JsonValue
        public Object toJson() {
            return value;
        }
    }

    @EqualsAndHashCode
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    class ObjectLink implements Link {
        public final String href;
        public final Meta meta;

        public ObjectLink(String href) {
            this(href, null);
        }

        @JsonCreator
        public ObjectLink(@JsonProperty("href") String href, @JsonProperty("meta") Meta meta) {
            this.href = href;
            this.meta = meta;
        }
    }
}
