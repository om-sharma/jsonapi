package org.dialectic.jsonapi.extension.pagination;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class PaginationMetaInformation {
    private long totalElements;

    private int totalPages;

    public PaginationMetaInformation(long totalElements, int totalPages) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    @JsonValue
    public Map getInformation() {
        return new HashMap<String, Object>() {{
            put("pagination", new HashMap<String, Object>() {{
                put("totalElements", totalElements);
                put("totalPages", totalPages);
            }});
        }};
    }

}
