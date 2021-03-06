package com.triofantastico.practiceproject.config.propertybased;

import org.aeonbits.owner.Config;

import static com.triofantastico.practiceproject.constant.PropertyNameConstant.TARGET_ENV;

@Config.Sources("file:${user.dir}/src/test/resources/properties/auth.properties")
@Config.LoadPolicy(Config.LoadType.MERGE)
public interface AuthConfig extends Config {

    @Key("${" + TARGET_ENV + "}.apiKey")
    String apikey();
}
