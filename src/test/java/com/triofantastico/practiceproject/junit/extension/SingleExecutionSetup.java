package com.triofantastico.practiceproject.junit.extension;

import com.triofantastico.practiceproject.config.yamlbased.YamlConfigHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

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
    }
}