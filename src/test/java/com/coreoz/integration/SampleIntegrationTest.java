package com.coreoz.integration;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import com.coreoz.guice.TestModule;
import com.coreoz.webservices.api.ExampleWs;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(GuiceTestRunner.class)
@GuiceModules(TestModule.class)
public class SampleIntegrationTest {
    @Inject
    ExampleWs exampleWs;
    @Test
    public void integration_test_scenario_description() {
        Assertions
            .assertThat(exampleWs.test("World").getName())
            .isEqualTo("hello World\nA configuration value");
    }
}
