package io.apicurio.registry.storage.impl.gitops;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * @author Jakub Senko <em>m@jsenko.net</em>
 */
public class YAMLObjectMapper {

    public static ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper(new YAMLFactory());
        MAPPER.findAndRegisterModules();
    }
}
