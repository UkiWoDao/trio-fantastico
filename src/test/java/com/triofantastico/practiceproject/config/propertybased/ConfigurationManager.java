package com.triofantastico.practiceproject.config.propertybased;

import org.aeonbits.owner.ConfigCache;

import static com.triofantastico.practiceproject.constant.PropertyNameConstant.TARGET_ENV;
import static org.aeonbits.owner.util.Collections.map;

public final class ConfigurationManager {

    private ConfigurationManager() {
        throw new AssertionError("Instantiation attempted from within class");
    }

    public static AuthConfig getAuthConfigInstance() {
        String targetEnvironment = System.getProperty(TARGET_ENV);
        if (targetEnvironment == null) {
            throw new IllegalArgumentException("target_env system property is not set");
        }
        // this overloaded method allows us to do dynamic property lookup via system property e.g. "${TARGET_ENV}.someKey"
        return ConfigCache.getOrCreate(AuthConfig.class, map(TARGET_ENV, System.getProperty(TARGET_ENV)));
    }
}
