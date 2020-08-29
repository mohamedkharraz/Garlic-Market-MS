package com.garlicode.garlicodemarket.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonFlattener {

    //private Map<String, JsonNode> json = new LinkedHashMap<>(64);
    private final JsonNode root;

    public JsonFlattener(JsonNode node) {
        this.root = Objects.requireNonNull(node);
    }

    public Map<String, JsonNode> flatten() {
        Map<String, JsonNode> map = new HashMap<String, JsonNode>();
        process(root, "", map);
        return map;
    }

    private void process(JsonNode node, String prefix, Map<String, JsonNode> map) {
        if (node.isObject()) {
            ObjectNode object = (ObjectNode) node;
            object
                    .fields()
                    .forEachRemaining(
                            entry -> {
                                process(entry.getValue(), prefix + (prefix.isEmpty() ? "" : ".") + entry.getKey(), map);
                            });
        } else if (node.isArray()) {
            ArrayNode array = (ArrayNode) node;
            AtomicInteger counter = new AtomicInteger();
            array
                    .elements()
                    .forEachRemaining(
                            item -> {
                                process(item, prefix + (prefix.isEmpty() ? "" : ".") + counter.getAndIncrement(), map);
                            });
        } else {
            map.put(prefix, node);
        }
    }
}
