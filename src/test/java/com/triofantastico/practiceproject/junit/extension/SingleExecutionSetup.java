package com.triofantastico.practiceproject.junit.extension;

import com.triofantastico.practiceproject.config.yamlbased.YamlConfigHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import static com.triofantastico.practiceproject.constant.PropertyNameConstant.TARGET_ENV;

@Slf4j
public class SingleExecutionSetup implements TestExecutionListener {

    @SneakyThrows
    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {

        // load environment-specific config from yaml
        String envName = System.getProperty(TARGET_ENV);
        log.info("Running test suite on: " + envName.toUpperCase(Locale.ROOT) + " environment");
        YamlConfigHandler.setConfigByEnvironmentName(envName);
        checkRequiredPropertiesFilesExist();
    }

    private static void checkRequiredPropertiesFilesExist() {
        final List<Path> requiredPropertiesFiles = List.of(Path.of("src/test/resources/properties/auth.properties"));

        for (Path path : requiredPropertiesFiles) {
            if (Files.notExists(path)) {
                throw new RuntimeException("Required property file at path: " + path + ", is missing");
            }
        }
    }
}
