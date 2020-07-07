package com.github.andersonbfn;

import java.util.HashMap;
import java.util.Map;
import org.testcontainers.containers.PostgreSQLContainer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class DatabaseLifecycle implements QuarkusTestResourceLifecycleManager {
  
  private static PostgreSQLContainer<?> PG = new PostgreSQLContainer<>("postgres:latest");

  @Override
  public Map<String, String> start() {
    PG.start();
    final Map<String, String> props = new HashMap<>();
    props.put("quarkus.datasource.url", PG.getJdbcUrl());
    props.put("quarkus.datasource.username", PG.getUsername());
    props.put("quarkus.datasource.password", PG.getPassword());
    return props;
  }

  @Override
  public void stop() {
    if (PG != null) {
      PG.stop();
    }
  }

}
