package org.dialectic.jsonapi;

import org.junit.Test;

import java.util.HashMap;

import static org.dialectic.jsonapi.testsupport.TestSupport.jString;
import static org.dialectic.jsonapi.testsupport.TestSupport.toJsonNode;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MetaTest {
    @Test
    public void merge_mergesTheContentsOfProvidedObject() {
        Meta meta = Meta.ofEntry("a", "b");
        assertThat(toJsonNode(meta).toString(), is(jString("{'a':'b'}")));

        meta.merge(Meta.ofEntry("c", "d"));

        assertThat(toJsonNode(meta).toString(), is(jString("{'a':'b','c':'d'}")));

        meta.merge(Meta.of(new HashMap<String, Object>() {{
            put("c", "p");
            put("m", "n");
        }}));

        assertThat(toJsonNode(meta).toString(), is(jString("{'a':'b','c':'p','m':'n'}")));

        meta.merge(Meta.of(new HashMap<String, Object>() {{
            put("c", "p");
            put("m", "n");
        }}));

        assertThat(toJsonNode(meta).toString(), is(jString("{'a':'b','c':'p','m':'n'}")));

    }


}