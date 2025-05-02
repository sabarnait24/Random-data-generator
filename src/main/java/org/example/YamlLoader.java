package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;

public class YamlLoader {

    public static <T> T loadSection(String filePath, String section, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            var rootNode = mapper.readTree(new File(filePath));
            var sectionNode = rootNode.get(section);

            if (sectionNode == null) {
                throw new RuntimeException("Section '" + section + "' not found in YAML.");
            }

            return mapper.treeToValue(sectionNode, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load section: " + section, e);
        }
    }

}
