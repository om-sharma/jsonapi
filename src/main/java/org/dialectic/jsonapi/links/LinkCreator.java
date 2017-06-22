package org.dialectic.jsonapi.links;

import lombok.SneakyThrows;

import java.util.Map;

import static org.dialectic.jsonapi.JsonApiObjectMapper.readToClass;

public class LinkCreator {
    @SneakyThrows
    public static Link create(Object link) {
        if (link == null) return null;

        Class<? extends Link> linkClass = Map.class.isAssignableFrom(link.getClass()) ? Link.ObjectLink.class : Link.StringLink.class;
        return readToClass(link, linkClass);

    }
}
