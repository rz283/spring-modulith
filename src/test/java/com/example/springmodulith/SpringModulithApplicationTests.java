package com.example.springmodulith;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.test.ApplicationModuleTest;

@ApplicationModuleTest
class SpringModulithApplicationTests {

    @Test
    void contextLoads() {
        ApplicationModules.of(SpringModulithApplication.class).verify();
    }

}
