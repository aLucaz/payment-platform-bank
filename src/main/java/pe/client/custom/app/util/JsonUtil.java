package pe.client.custom.app.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class JsonUtil {

    private JsonUtil() {
    }

    public static String getField(String name, JsonNode rootNode) {
        List<JsonNode> parents = rootNode.findParents(name);
        if (parents != null && !parents.isEmpty()) {
            for (JsonNode parent : parents) {
                JsonNode field = parent.get(name);
                if (field != null && field.isTextual()) {
                    return field.textValue();
                }
            }
        }
        return null;
    }
}
