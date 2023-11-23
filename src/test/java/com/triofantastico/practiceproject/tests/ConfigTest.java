package com.triofantastico.practiceproject.tests;

import com.triofantastico.practiceproject.config.propertybased.ConfigurationManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigTest {

    @Test
    void owner_lib_can_read_property() {
        String qaApiKey = ConfigurationManager.getAuthConfigInstance().apikey();
        Assertions.assertEquals("qa-test" , qaApiKey);
    }
}
