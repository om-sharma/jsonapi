package org.dialectic.jsonapi.links;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import static org.dialectic.jsonapi.links.LinkCreator.create;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaginationLinks {
    @JsonProperty("first")
    private Link first;

    @JsonProperty("last")
    private Link last;

    @JsonProperty("prev")
    private Link prev;

    @JsonProperty("next")
    private Link next;

    public PaginationLinks(Link first, Link last, Link next, Link prev) {
        this.first = first;
        this.last = last;
        this.prev = prev;
        this.next = next;
    }

    public static PaginationLinks links(String first, String last, String next, String previous) {
        return new PaginationLinks(
                new Link.StringLink(first),
                new Link.StringLink(last),
                new Link.StringLink(next),
                new Link.StringLink(previous)
        );
    }

    public static PaginationLinks links(Link first, Link last, Link next, Link previous) {
        return new PaginationLinks(first, last, next, previous);
    }

    @JsonCreator
    private static PaginationLinks deserialize(
            @JsonProperty("first") Object first,
            @JsonProperty("previous") Object previous,
            @JsonProperty("next") Object next,
            @JsonProperty("last") Object last) {
        return links(
                create(first),
                create(last),
                create(next),
                create(previous)
        );
    }
}
