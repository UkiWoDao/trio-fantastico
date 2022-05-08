package com.triofantastico.practiceproject.config.yamlbased;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
public class YamlConfigHandler {

    private @Getter @Setter static YamlConfigModel.Profile environmentConfig;

    private YamlConfigHandler() { throw new AssertionError("Instantiation attempted from within class"); }

    public static void setConfigByEnvironmentName(String envName) throws IOException {
        final String yamlPropertiesPath = "src/test/resources/environment.yaml";
        File yamlFile = new File(yamlPropertiesPath);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        YamlConfigModel environmentList = mapper.readValue(yamlFile, YamlConfigModel.class);

        try {
            setEnvironmentConfig(environmentList.getProfiles().stream().filter(env -> env.getName().equals(envName)).findAny()
                    .orElseThrow());
        } catch (NoSuchElementException ex) {
            log.error("Config for environment: \"" + envName + "\" is not present in file \"" + yamlPropertiesPath + "\";' " +
                    ex.getMessage());
        }
    }
}