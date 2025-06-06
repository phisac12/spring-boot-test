package br.com.zaix.integration_tests.tests_containers;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initiazer.class)
public class AbstractIntegrationTest {

    static class Initiazer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.1.0").withDatabaseName("spring_boot_test");

        private static void startContainers() {
            Startables.deepStart(Stream.of(mysql)).join();
        }

        private Map<String, String> createConnectionConfiguration() {
            return Map.of(
                "spring.datasource.url", mysql.getJdbcUrl(),
                "spring.datasource.username", mysql.getUsername(),
                "spring.datasource.password", mysql.getPassword()
                
         );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testContainers =
             new MapPropertySource("testContainers", (Map) createConnectionConfiguration());
            environment.getPropertySources().addFirst(testContainers);
        }


    }

}
