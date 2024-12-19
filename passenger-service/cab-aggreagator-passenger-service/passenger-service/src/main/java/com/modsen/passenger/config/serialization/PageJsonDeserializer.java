package com.modsen.passenger.config.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageJsonDeserializer extends JsonDeserializer<Page> {

    @Override
    public Page deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode contentNode = node.get("content");
        JsonNode totalElementsNode = node.get("totalElements");
        JsonNode totalPagesNode = node.get("totalPages");
        JsonNode sizeNode = node.get("size");
        JsonNode numberNode = node.get("number");

        List<Object> content = new ArrayList<>();
        if (contentNode.isArray()) {
            for (JsonNode elementNode : contentNode) {
                content.add(jsonParser.getCodec().treeToValue(elementNode, Object.class));
            }
        }

        int totalElements = (totalElementsNode != null) ? totalElementsNode.asInt() : 0;
        int totalPages = (totalPagesNode != null) ? totalPagesNode.asInt() : 0;
        int size = (sizeNode != null) ? sizeNode.asInt() : 0;
        int number = (numberNode != null) ? numberNode.asInt() : 0;

        return new PageImpl<>(content, PageRequest.of(number, size), totalElements);
    }
}
